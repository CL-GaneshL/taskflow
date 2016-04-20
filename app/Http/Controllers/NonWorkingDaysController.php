<?php

namespace App\Http\Controllers;

use Config;
use App\NonWorkingDay;
use Illuminate\Http\Request;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class NonWorkingDaysController extends Controller {

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

        $non_working_days = NonWorkingDaysController:: getNonWorkingDays();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => $non_working_days,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
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

        // ---------------------------------------------------
        // - create and store the non working day record
        // ---------------------------------------------------
        $nwd = NonWorkingDay::create($request->all());

        // ---------------------------------------------------
        // \Log::debug('$nwd = ' . print_r($nwd, true));
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'data' => $nwd,
                    'message' => 'Non-Working Day created succesfully'
                        ], 200);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id) {
        //
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
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {

        // ---------------------------------------------------
        // \Log::debug('destroy $id = ' . print_r($id, true));
        // ---------------------------------------------------

        NonWorkingDay::destroy($id);

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'data' => $id,
                    'message' => 'Non-Working Day deleted succesfully'
                        ], 200);
    }

    /**
     * 
     */
    public static function getNonWorkingDays() {

        // --------------------------------------------------------
        // - get the list of all non working days 
        // --------------------------------------------------------
        $time = mktime(0, 0, 0) + ( 365 * 24 * 60 * 60);
        $one_year_ahead = date('Y-m-d', $time);
        $earliest_start_date = null;

        $first_of_this_month = date('Y-m-01', mktime(0, 0, 0));

        // start at the earliest date of an open project
        $earliest_project_start_date = \DB::table('projects')
                ->select('projects.start_date')
                ->where('projects.open', '=', '1')
                ->orderBy('projects.start_date', 'asc')
                ->first();

        if (empty($earliest_project_start_date)) {
            $earliest_start_date = $first_of_this_month;
        } else {
            // display all non working days for the month 
            // in case or there is not projects open
            // or they start this month.
            if (strtotime($earliest_project_start_date->start_date) < strtotime($first_of_this_month)) {
                $earliest_start_date = $earliest_project_start_date->start_date;
            } else {
                $earliest_start_date = $first_of_this_month;
            }
        }
        // ---------------------------------------------------
        // \Log::debug('$earliest_project_start_date = ' . print_r($earliest_project_start_date, true));
        // \Log::debug('$one_year_in_future = ' . print_r($one_year_ahead, true));
        // ---------------------------------------------------

        $non_working_days = \DB::table('non_working_days')
                ->select('*')
                ->whereBetween('non_working_days.date', array($earliest_start_date, $one_year_ahead))
                ->orderBy('non_working_days.date', 'asc')
                ->get();

        // ---------------------------------------------------
        // \Log::debug('$non_working_days = ' . print_r($non_working_days, true));
        // ---------------------------------------------------
        return $non_working_days;
    }

}
