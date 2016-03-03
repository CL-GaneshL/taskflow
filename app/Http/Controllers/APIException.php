<?php

namespace App\Http\Controllers;

/**
 * Description of ControllerException
 *
 * @author wdmtraining
 */
class APIException extends \Exception {

    // Redefine the exception so message isn't optional
    public function __construct($message, $code = 0, Exception $previous = null) {


        // make sure everything is assigned properly
        parent::__construct($message, $code, $previous);
    }

    // custom string representation of object
    public function __toString() {
        return __CLASS__ . ": [{$this->code}]: {$this->message}\n";
    }

    public function customFunction() {
        echo "A custom function for this type of exception\n";
    }

}
