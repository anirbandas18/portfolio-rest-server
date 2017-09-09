package com.teenthofabud.portfolio.core.handler;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.util.PathMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.teenthofabud.portfolio.vo.ResponseVO;
import com.teenthofabud.portfolio.vo.ValidationVO;


@RestControllerAdvice
public class ControllerResponseHandler implements ResponseBodyAdvice<Object>{
	
	@Value("${freelancer.avatar.uri.regex}")
	private String freelancerAvatarURIRegex;
	@Value("${freelancer.resume.uri.regex}")
	private String freelancerResumeURIRegex;
	@Autowired
	private PathMatcher pathMatcher;
	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	@Value("${validation.error.template}")
	private String validationErrorTemplate;
	
	private static final Logger LOG = LoggerFactory.getLogger(ControllerResponseHandler.class);
	
	@ExceptionHandler(value = { HttpStatusCodeException.class })
	public ResponseEntity<ResponseVO> handleControllerExceptions(HttpStatusCodeException e) {
		ResponseVO  body = new ResponseVO();
		body.setStatus(e.getStatusCode().name());
		body.setMessage(e.getStatusText());
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, e.getStatusCode());
		return response;
	}
	
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationVO> handleValidationErrors(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<ResponseVO> errors = bindingResult.getFieldErrors().stream()
				.map(x -> new ResponseVO(x.getField(), x.getDefaultMessage())).collect(Collectors.toList());
		String message = validationErrorTemplate.replace(exceptionCausePlaceholder, bindingResult.getObjectName());
		ValidationVO body = new ValidationVO(message, errors);
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
		return response;
	}
	
	@ExceptionHandler(value = MultipartException.class)
	public ResponseEntity<ValidationVO> handleMultipartFileException(MultipartException e) {
		ValidationVO body = new ValidationVO(e.getMessage());
		HttpStatus code = e instanceof MaxUploadSizeExceededException ? HttpStatus.BAD_REQUEST : HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, code);
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
		// TODO Auto-generated method stub
		String uri = request.getURI().getPath();
		if(!pathMatcher.match(freelancerAvatarURIRegex, uri) && !pathMatcher.match(freelancerResumeURIRegex, uri)) {
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			response.getHeaders().setDate(System.currentTimeMillis());;
			LOG.info("Response headers added to URI: {}", uri);
		}
		return body;
	}
	
}
