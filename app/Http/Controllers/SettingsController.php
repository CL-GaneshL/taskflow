<?php

namespace App\Http\Controllers;

use Config;
use App\Helpers\SettingsHelper;
use App\Http\Controllers\Controller;
use Symfony\Component\Process\Process;

class SettingsController extends Controller {

    private $HTTP_OK = null;
    private $HTTP_INTERNAL_SERVER_ERROR = null;
    private $HTTP_INTERNAL_SERVER_ERROR_MSG = null;

    public function __construct() {

        $this->HTTP_OK = Config::get('caratlane.dbconstants.HTTP_OK');
        $this->HTTP_INTERNAL_SERVER_ERROR = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR');
        $this->HTTP_INTERNAL_SERVER_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR_MSG');

        // -------------------------------------
        // Apply the jwt.auth middleware to all methods in this controller
        // -------------------------------------
        $this->middleware('jwt.auth');
    }

    /**
     * 
     */
    public function getTaskflowConfiguration() {

        $settings = new SettingsHelper();

        $settings->log('');
        $settings->log('............................');
        $settings->log('TaskGenerator configuration :');

        $settings->log('   - debug mode    : [' . $settings->getDebugMode() . ']');
        $settings->log('   - java binary   : [' . $settings->getJavaBinary() . ']');
        $settings->log('   - jar file      : [' . $settings->getJarFile() . ']');
        $settings->log('   - log path      : [' . $settings->getLogPath() . ']');
        $settings->log('   - db host       : [' . $settings->getDbHost() . ']');
        $settings->log('   - db port       : [' . $settings->getDbPort() . ']');
        $settings->log('   - database      : [' . $settings->getDatabase() . ']');
        $settings->log('   - db username   : [' . $settings->getDbUsername() . ']');
        $settings->log('   - db password   : [' . $settings->getDbPassword() . ']');
        $settings->log('   - command line  : [' . $settings->getCommandLine("") . ']');

        $action = 'version';
        $java_bin = $settings->getJavaBinary();
        $jar = $settings->getJarFile();
        $commandline = $settings->getCommandLine($action);
        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;

        $process = new Process($command);
        $process->start();

        $settings->log("TaskGenerator version : ");
        $process->wait(function ($type, $buffer)use ($settings) {
            $str = str_replace("\r\n", "", $buffer);
            if (strlen($str) != 0) {
                $settings->log($str);
            }
        });

        $settings->log('--');

        return $settings->publish("TaskGenerator configuration.");
    }

    /**
     * 
     */
    public function getJavaConfiguration() {

        $settings = new SettingsHelper();

        $settings->log('');
        $settings->log('............................');
        $settings->log('TaskGenerator Java version :');

        $process = new Process($settings->getJavaBinary() . ' ' . '-version');
        $process->start();
        $process->wait(function ($type, $buffer)use ($settings) {

            $str = str_replace("\r\n", "", $buffer);
            if (strlen($str) != 0) {
                $settings->log($str);
            }
        });

        $settings->log('--');

        return $settings->publish("TaskGenerator Java version.");
    }

}
