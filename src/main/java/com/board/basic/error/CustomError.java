package com.board.basic.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomError implements ErrorController {

    @GetMapping("/error")
    public String handlerError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            int statusCode = Integer.valueOf(status.toString());

            // 404 ERROR = NOT FOUND ERROR
            if(statusCode == HttpStatus.NOT_FOUND.value())
                return "/error/error404";

            // 500 ERROR = SERVER ERROR
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
                return "/error/error500";
        }

        // ERRORS = ETC ERROR
        return "/error/errors";
    }
}
