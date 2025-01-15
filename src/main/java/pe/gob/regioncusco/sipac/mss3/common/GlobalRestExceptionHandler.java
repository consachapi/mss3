package pe.gob.regioncusco.sipac.mss3.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.Date;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> exception(Exception ex, HttpServletRequest request){
        ex.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            DuplicateKeyException.class,
            WebExchangeBindException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> badRequest(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
