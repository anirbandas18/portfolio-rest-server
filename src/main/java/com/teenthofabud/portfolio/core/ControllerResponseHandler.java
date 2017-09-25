package com.teenthofabud.portfolio.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.teenthofabud.portfolio.core.exception.InvalidSearchParametersException;
import com.teenthofabud.portfolio.service.UtilityService;
import com.teenthofabud.portfolio.vo.ResponseVO;
import com.teenthofabud.portfolio.vo.ValidationVO;

@RestControllerAdvice
public class ControllerResponseHandler /* extends ResponseEntityExceptionHandler */ implements
		ResponseBodyAdvice<Object>/* , HandlerExceptionResolver */ {

	@Autowired
	private UtilityService util;

	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	@Value("${validation.error.template}")
	private String validationErrorTemplate;
	@Value("#{'${filtered.response.header.paths}'.split(',')}")
	private List<String> filteredResponseHeaderPaths;

	private static final Logger LOG = LoggerFactory.getLogger(ControllerResponseHandler.class);

	@ExceptionHandler(value = { HttpStatusCodeException.class })
	public ResponseEntity<ResponseVO> handleControllerExceptions(HttpStatusCodeException e) {
		ResponseVO body = new ResponseVO();
		body.setStatus(e.getStatusCode().name());
		body.setMessage(e.getStatusText());
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, e.getStatusCode());
		return response;
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationVO> handleRequestBodyValidationErrors(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		String modelName = bindingResult.getObjectName();
		LOG.info("{} property validation failed : {}", modelName, e);
		List<ResponseVO> errors = bindingResult.getFieldErrors().stream()
				.map(x -> new ResponseVO(x.getField(), x.getDefaultMessage())).collect(Collectors.toList());
		String message = validationErrorTemplate.replace(exceptionCausePlaceholder, modelName);
		ValidationVO body = new ValidationVO(message, errors);
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
		return response;
	}

	@ExceptionHandler(value = MultipartException.class)
	public ResponseEntity<ValidationVO> handleMultipartFileException(MultipartException e) {
		LOG.info("Error validating file while uploading: {}", e);
		ValidationVO body = new ValidationVO(e.getMessage());
		HttpStatus code = e instanceof MaxUploadSizeExceededException ? HttpStatus.BAD_REQUEST
				: HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, code);
		return response;
	}

	@ExceptionHandler(value = {ConstraintViolationException.class, InvalidSearchParametersException.class})
	public ResponseEntity<ValidationVO> handleRequestQueryParamValidationErrors(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		List<ResponseVO> errors =  new ArrayList<>();
		Set<Class<? extends Payload>> payloads = new LinkedHashSet<>();
		violations.forEach(v -> {
			ConstraintDescriptor<?> d = v.getConstraintDescriptor();
			List<Class<? extends Payload>> plds = new ArrayList<>(d.getPayload());
			payloads.addAll(plds);
			ResponseVO rvo = new ResponseVO();
			rvo.setStatus(plds.get(0).getSimpleName());
			rvo.setMessage(v.getMessage());
			errors.add(rvo);
		});
		Set<String> causes = payloads.stream().map(p -> p.getEnclosingClass().getSimpleName()).collect(Collectors.toSet());
		String cause = String.join(",", causes);
		LOG.info("Error validating request payload: {}", cause);
		String message = StringUtils.hasText(e.getMessage()) ? e.getMessage() : validationErrorTemplate.replace(exceptionCausePlaceholder, cause);
		ValidationVO body = new ValidationVO(message, errors);
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
		return response;
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// handle UnknownServerHttpRequestImplException
		HttpServletRequest servletRequest = util.springToServletHttpRequestConverter(request);
		String uri = util.matchesRequest(filteredResponseHeaderPaths, servletRequest);
		if (uri.length() != 0) {
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			response.getHeaders().setDate(System.currentTimeMillis());
			;
			LOG.info("Response headers added to URI: {}", uri);
		}
		return body;
	}

}
