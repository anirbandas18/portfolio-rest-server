package com.teenthofabud.portfolio.core.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.teenthofabud.portfolio.service.UtilityService;
import com.teenthofabud.portfolio.vo.ResponseVO;
import com.teenthofabud.portfolio.vo.ValidationVO;

@RestControllerAdvice
@Component
public class PortfolioResponseHandler /*extends  ResponseEntityExceptionHandler */implements ResponseBodyAdvice<Object>/*, HandlerExceptionResolver*/ {

	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	@Value("${validation.error.template}")
	private String validationErrorTemplate;
	@Value("#{'${filtered.response.header.paths}'.split(',')}")
	private List<String> filteredResponseHeaderPaths;
	@Autowired
	private UtilityService util;

	private static final Logger LOG = LoggerFactory.getLogger(PortfolioResponseHandler.class);

	@ExceptionHandler(value = { HttpStatusCodeException.class })
	public ResponseEntity<ResponseVO> handleControllerExceptions(HttpStatusCodeException e) {
		ResponseVO body = new ResponseVO();
		body.setStatus(e.getStatusCode().name());
		body.setMessage(e.getStatusText());
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, e.getStatusCode());
		return response;
	}
	
	/*@ExceptionHandler(value = { UnknownServerHttpRequestImplException.class })
	public ResponseEntity<ResponseVO> handleHandlerExceptions(HttpStatusCodeException e) {
		ResponseVO body = new ResponseVO();
		body.setStatus(e.getStatusCode().name());
		body.setMessage(e.getStatusText());
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, e.getStatusCode());
		return response;
	}*/

	
	/*
	 * replaced by handleMethodArgumentNotValid method of parent class,
	 * since handleException of parent class already has 
	 * @ExceptionHandler(value = MethodArgumentNotValidException.class) defined,
	 * hence ambiguity
	 * 
	 */ @ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationVO> handleRequestValidationErrors(MethodArgumentNotValidException e) {
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
		HttpStatus code = e instanceof MaxUploadSizeExceededException ? HttpStatus.BAD_REQUEST
				: HttpStatus.UNSUPPORTED_MEDIA_TYPE;
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
		// handle UnknownServerHttpRequestImplException
		HttpServletRequest servletRequest = util.springToServletHttpRequestConverter(request);
		String uri = util.matchesRequest(filteredResponseHeaderPaths, servletRequest);
		if (uri.length() != 0) {
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			response.getHeaders().setDate(System.currentTimeMillis());;
			LOG.info("Response headers added to URI: {}", uri);
		}
		return body;
	}

	/*@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setDateHeader("date", System.currentTimeMillis());
		try {
			response.getWriter().write(ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView();
	}
	*/
	
}
