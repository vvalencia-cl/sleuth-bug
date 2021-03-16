package bug.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotFoundException(message: String) : ResponseStatusException(HttpStatus.NOT_FOUND, message)

class AlreadyExistsException(message: String) : ResponseStatusException(HttpStatus.BAD_REQUEST, message)

class BadValueException(message: String) : ResponseStatusException(HttpStatus.BAD_REQUEST, message)

class NotAllowedException(message: String) : ResponseStatusException(HttpStatus.FORBIDDEN, message)
