<?php

namespace App\Http\Controllers;

use Config;
use Illuminate\Http\Request;
use App\Http\Requests;
use App\Http\Controllers\Controller;
use Symfony\Component\Process\Process;
use Symfony\Component\Process\Exception\ProcessFailedException;

class AllocateController extends Controller {

    /**
     * 
     */
    public function allocate() {

        \Log::debug('Task allocation : ...........................................');
        \Log::debug('Settings : ');

        // debug mode
        $mode = Config::get('app.debug');
        if ($mode === true) {
            $mode = 'debug';
        } else {
            $mode = '';
        }

        // java and .jar 
        $java_bin_dir = Config::get('java.JAVA_HOME') . DIRECTORY_SEPARATOR . 'bin';
        $java_bin = '"' . $java_bin_dir . DIRECTORY_SEPARATOR . 'java' . '"';
        $jar_path = base_path('resources/bin');
        $jar_file = Config::get('caratlane.constants.TASK_GENERATOR_JAR');
        $jar = '"' . $jar_path . DIRECTORY_SEPARATOR . $jar_file . '"';

        \Log::debug('   - java binary : [' . $java_bin . ']');
        \Log::debug('   - jar file : [' . $jar . ']');

        // log file
        $log_path = storage_path('logs');
        $log_file = Config::get('caratlane.constants.TASK_GENERATOR_LOG_FILE');
        $logpath = '"' . $log_path . DIRECTORY_SEPARATOR . $log_file . '"';

        \Log::debug('   - log directory : [' . $log_path . ']');

        // database config data
        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        $host = $config['host'];
        $port = $config['port'];
        $database = $config['database'];
        $username = $config['username'];
        $password = $config['password'];

        // task generation
        $action = 'generate';

        // command line
        $commandline = '';
        $commandline = $commandline . ' -host=' . $host;
        $commandline = $commandline . ' -port=' . $port;
        $commandline = $commandline . ' -database=' . $database;
        $commandline = $commandline . ' -username=' . $username;
        $commandline = $commandline . ' -password=' . $password;
        $commandline = $commandline . ' -logpath=' . $logpath;
        $commandline = $commandline . ' -mode=' . $mode;
        $commandline = $commandline . ' -action=' . $action;

        \Log::debug('Java version : ...........................................');
        $process = new Process($java_bin . ' ' . '-version');
        $process->start();
        $process->wait(function ($type, $buffer) {
            \Log::debug($buffer);
        });

        \Log::debug('Allocation : ...........................................');
        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;
        \Log::debug('   - Command : [' . $command . ']');
        $process = new Process($command);
        $process->start();

        $process->wait(function ($type, $buffer) {
            \Log::debug($buffer);
        });
    }

    public function reset() {

        \Log::debug('Task reset : ...........................................');
        \Log::debug('Settings : ');

        // debug mode
        $mode = Config::get('app.debug');
        if ($mode === true) {
            $mode = 'debug';
        } else {
            $mode = '';
        }

        // java and .jar 
        $java_bin_dir = Config::get('java.JAVA_HOME') . DIRECTORY_SEPARATOR . 'bin';
        $java_bin = '"' . $java_bin_dir . DIRECTORY_SEPARATOR . 'java' . '"';
        $jar_path = base_path('resources/bin');
        $jar_file = Config::get('caratlane.constants.TASK_GENERATOR_JAR');
        $jar = '"' . $jar_path . DIRECTORY_SEPARATOR . $jar_file . '"';

        \Log::debug('   - java binary : [' . $java_bin . ']');
        \Log::debug('   - jar file : [' . $jar . ']');

        // log file
        $log_path = storage_path('logs');
        $log_file = Config::get('caratlane.constants.TASK_GENERATOR_LOG_FILE');
        $logpath = '"' . $log_path . DIRECTORY_SEPARATOR . $log_file . '"';

        \Log::debug('   - log directory : [' . $log_path . ']');

        // database config data
        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        $host = $config['host'];
        $port = $config['port'];
        $database = $config['database'];
        $username = $config['username'];
        $password = $config['password'];

        // task generation
        $action = 'reset';

        // command line
        $commandline = '';
        $commandline = $commandline . ' -host=' . $host;
        $commandline = $commandline . ' -port=' . $port;
        $commandline = $commandline . ' -database=' . $database;
        $commandline = $commandline . ' -username=' . $username;
        $commandline = $commandline . ' -password=' . $password;
        $commandline = $commandline . ' -logpath=' . $logpath;
        $commandline = $commandline . ' -mode=' . $mode;
        $commandline = $commandline . ' -action=' . $action;

        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;
        \Log::debug('   - Command : [' . $command . ']');
        $process = new Process($command);
        $process->start();

        $process->wait(function ($type, $buffer) {
            \Log::debug($buffer);
        });
    }

}
