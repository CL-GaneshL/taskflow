<?php

namespace App\Http\Controllers;

use Config;
use App\Task;
use App\TaskAllocation;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class EmployeesTasksController extends Controller {

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
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index() {
        
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create() {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request) {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id, $range) {
        
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id) {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id) {

        $allocation_id = $request->id;

        // ---------------------------------------------------
        \Log::debug('$allocation_id = ' . $allocation_id);
        // ---------------------------------------------------
        // check that the task belongs to an open project 
        $task_allocations = \DB::table('v_tasks_info')
                ->select('open')
                ->where('v_tasks_info.id', '=', $allocation_id)
                ->get();

        $allocation = $task_allocations[0];
        $open = $allocation->open;

        if ($open === 1) {
            // ---------------------------------------------------
            // project still open
            // ---------------------------------------------------
            $allocation = TaskAllocation::find($allocation_id);
            $allocation->task_id = trim($request->task_id) !== '' ? $request->task_id : null;
            $allocation->employee_id = trim($request->employee_id) !== '' ? $request->employee_id : null;
            $allocation->start_date = trim($request->start_date) !== '' ? $request->start_date : null;
            $allocation->completion = trim($request->completion) !== '' ? $request->completion : null;
            $allocation->nb_products_completed = trim($request->nb_products_completed) !== '' ? $request->nb_products_completed : null;
            $allocation->completed = trim($request->completed) !== '' ? $request->completed : null;
            $allocation->duration = trim($request->duration) !== '' ? $request->duration : null;

            // ---------------------------------------------------
            \Log::debug('$allocation = ' . $allocation);
            // ---------------------------------------------------
            // ---------------------------------------------------
            // save the allocation 
            // ---------------------------------------------------
            $allocation->save();

            $task_allocations = \DB::table('v_tasks_info')
                    ->select('v_tasks_info.*')
                    ->where('v_tasks_info.id', '=', $allocation_id)
                    ->get();

            $updatedTask = $task_allocations[0];

            // ---------------------------------------------------
            // and send a response to the client
            // ---------------------------------------------------

            return response()->json([
                        'message' => 'Task updated succesfully',
                        'data' => $updatedTask
                            ], 200);
        } else {
            // ---------------------------------------------------
            // project has been closed
            // ---------------------------------------------------

            return response()->json([
                        'message' => 'Project closed, this task cannot be updated',
                            ], 500);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {
        
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeeTasksData($id) {

        // ---------------------------------------------------
        // - list of all tasks for that employee $id
        // ---------------------------------------------------
        $tasks = \DB::table('v_tasks_info')
                ->select('v_tasks_info.*')
                ->where('v_tasks_info.employee_id', '=', $id)
                ->where('v_tasks_info.open', '=', 1)
                ->orderBy('v_tasks_info.id', 'asc')
                ->get();

        if (empty($tasks)) {
            $tasks = array();
        }

        // ---------------------------------------------------
        // \Log::debug('getEmployeeTasksData : nb tasks : ' . print_r(sizeof($tasks), true));
        // ---------------------------------------------------

        return $tasks;
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeeRecentTasksData($id) {

        // ---------------------------------------------------
        // - list of all recent tasks for the employee $id
        // ---------------------------------------------------
        $RECENT_TASKS_NB_MAX = 15;
        $plus_3_days = ( 3 * 24 * 60 * 60 ) + mktime(0, 0, 0);
        $plus_3_days_sql = date("Y-m-d", $plus_3_days);

        $tasks = \DB::table('v_tasks_info')
                ->select('v_tasks_info.*')
                ->where('v_tasks_info.employee_id', '=', $id)
                ->where('v_tasks_info.start_date', '<=', $plus_3_days_sql)
                ->where('v_tasks_info.open', '=', 1)
                ->orderBy('v_tasks_info.start_date', 'desc')
                ->take($RECENT_TASKS_NB_MAX)
                ->get();

        if (empty($tasks)) {
            $tasks = array();
        }

        // ---------------------------------------------------
        // \Log::debug('getEmployeeRecentTasksData : tasks : ' . print_r($tasks, true));
        // \Log::debug('getEmployeeRecentTasksData : nb tasks : ' . print_r(sizeof($tasks), true));
        // ---------------------------------------------------

        return $tasks;
    }

}
