<?php

namespace App\Helpers;

use Config;

/**
 * Description of SettingsHelper
 *
 * @author wdmtraining
 */
class SettingsHelper {

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

    public function getPhpInfo() {

        // catch the phpInfo data
        ob_start();
        phpinfo();
        $pinfo = ob_get_contents();
        ob_end_clean();

        // remove the body tag so it does not interfere with the app htmp code
        $pinfo = preg_replace('%^.*<body>(.*)</body>.*$%ms', '$1', $pinfo);

        // splash the data into the page
        echo $pinfo;
    }

    public function getDebugMode() {

        $mode = Config::get('app.debug');
        if ($mode === true) {
            $mode = 'debug';
        } else {
            $mode = '';
        }

        return $mode;
    }

    public function getJavaBinary() {

        $java_bin_dir = Config::get('java.JAVA_HOME') . DIRECTORY_SEPARATOR . 'bin';
        $java_bin = '"' . $java_bin_dir . DIRECTORY_SEPARATOR . 'java' . '"';

        return $java_bin;
    }

    public function getJarFile() {

        $jar_path = base_path('resources/bin');
        $jar_file = Config::get('caratlane.constants.TASK_GENERATOR_JAR');
        $jar = '"' . $jar_path . DIRECTORY_SEPARATOR . $jar_file . '"';

        return $jar;
    }

    public function getLogPath() {

        $log_path = storage_path('logs');
        $log_file = Config::get('caratlane.constants.TASK_GENERATOR_LOG_FILE');
        $logpath = '"' . $log_path . DIRECTORY_SEPARATOR . $log_file . '"';

        return $logpath;
    }

    public function getDbHost() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['host'];
    }

    public function getDbPort() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['port'];
    }

    public function getDatabase() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['database'];
    }

    public function getDbUsername() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['username'];
    }

    public function getDbPassword() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['password'];
    }

    public function getCommandLine($action) {

        $commandline = '';
        $commandline = $commandline . ' -host=' . $this->getDbHost();
        $commandline = $commandline . ' -port=' . $this->getDbPort();
        $commandline = $commandline . ' -database=' . $this->getDatabase();
        $commandline = $commandline . ' -username=' . $this->getDbUsername();
        $commandline = $commandline . ' -password=' . $this->getDbPassword();
        $commandline = $commandline . ' -logpath=' . $this->getLogPath();
        $commandline = $commandline . ' -mode=' . $this->getDebugMode();
        $commandline = $commandline . ' -action=' . $action;

        return $commandline;
    }

    public function getLaravelLogFileContent() {

        $log_path = storage_path('logs');
        $today = date('Y-m-d', time());
        $filename = "laravel-" . $today . '.log';
        $logpath = $log_path . DIRECTORY_SEPARATOR . $filename;

        $contents = '<p>';
        $contents .= 'Laravel log file :';
        $contents .= '   - path    : [' . $logpath . ']';

        $file_contents = file_get_contents($logpath);
        $contents .= nl2br($file_contents);

        $contents .= '</p>';

        echo $contents;
    }

    /**
     * 
     * @param type $str
     */
    public function log($str) {
        $this->buffer [] = $str;
    }

    /**
     * 
     * @return type
     */
    public function publish($msg) {

        // create a temporary buffer
        // this buffer will be return the the client
        // while the original buffer is destroy
        $response_buffer = array();

        // trace the msg in the log file
        $max = sizeof($this->buffer);
        for ($i = 0; $i < $max; $i++) {
            $str = utf8_encode($this->buffer[$i]);
            \Log::debug($str);

            // fill a temporary buffer with the message
            $response_buffer[] = $str;
        }

        // re-initialize the original buffer
        $this->buffer = array();

//        \Log::debug('$response_buffer : ' . print_r($response_buffer, true));
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
