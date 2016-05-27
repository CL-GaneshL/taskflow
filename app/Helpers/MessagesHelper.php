<?php

namespace App\Helpers;

use Config;

/**
 * Description of SettingsHelper
 *
 * @author wdmtraining
 */
class MessagesHelper {

    private $HTTP_OK = null;
    private $HTTP_INTERNAL_SERVER_ERROR = null;
    private $HTTP_INTERNAL_SERVER_ERROR_MSG = null;
    private $buffer = null;

    public function __construct() {

        $this->HTTP_OK = Config::get('caratlane.dbconstants.HTTP_OK');
        $this->HTTP_INTERNAL_SERVER_ERROR = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR');
        $this->HTTP_INTERNAL_SERVER_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR_MSG');

        $this->buffer = array();
    }

    /**
     * 
     * @param type $str
     */
    public function log($str) {
        array_push($this->buffer, $str);
    }

    /**
     * 
     * @return type
     */
    public function publish($msg) {

        \Log::debug('$this->buffer : ' . print_r($this->buffer, true));

        // create a temporary buffer
        // this buffer will be return the the client
        // while the original buffer is destroy
        $response_buffer = array();

        // trace the msg in the log file
        $max = sizeof($this->buffer);
        for ($i = 0; $i < $max; $i++) {
            
            $str = utf8_encode($this->buffer[$i]);

            // fill a temporary buffer with the message
            $response_buffer[] = $str;
        }

        // re-initialize the original buffer
        $this->buffer = array();

        \Log::debug('$response_buffer : ' . print_r($response_buffer, true));

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => $response_buffer, // sending back the logs
                    'message' => $msg
                        ], $this->HTTP_OK);
    }

}
