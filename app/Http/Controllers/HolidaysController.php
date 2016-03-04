<?php

namespace App\Http\Controllers;

use Config;
use App\Holiday;
use Illuminate\Http\Request;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class HolidaysController extends Controller {

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
        //
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
        // - create and store a new employee
        // ---------------------------------------------------
        $holiday = Holiday::create($request->all());

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'message' => 'Holiday created succesfully',
                    'data' => $holiday
                        ], 200);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id) {
        
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id) {
        
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id) {

        // ---------------------------------------------------
        // -update the non working day record 
        // ---------------------------------------------------
        $holiday = Holiday::find($id);

        $holiday->title = trim($request->title) !== '' ? $request->title : null;
        $holiday->employee_id = trim($request->employee_id) !== '' ? $request->employee_id : null;
        $holiday->start_date = trim($request->start_date) !== '' ? $request->start_date : null;
        $holiday->end_date = trim($request->end_date) !== '' ? $request->end_date : null;

        // ---------------------------------------------------
        \Log::debug('update : $holiday = ' . print_r($holiday, true));
        // ---------------------------------------------------

        $holiday->save();

        return response()->json([
                    'message' => 'Holiday updated succesfully',
                    'data' => $holiday
                        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {

        Holiday::destroy($id);

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'data' => $id,
                    'message' => 'Holiday event deleted succesfully'
                        ], 200);
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeeHolidaysData($id) {

        // ---------------------------------------------------
        // - get the employee's team
        // ---------------------------------------------------
        $holidays = \DB::table('v_holidays_info')
                ->select('v_holidays_info.*')
                ->where('v_holidays_info.employee_id', '=', $id)
                ->get();

        if (empty($holidays)) {
            $holidays = array();
        }

        return $holidays;
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getHolidaysData() {

        // ---------------------------------------------------
        // - get the employee's team
        // ---------------------------------------------------
        $holidays = \DB::table('v_holidays_info')
                ->select('v_holidays_info.*')
                ->get();

        if (empty($holidays)) {
            $holidays = array();
        }

        return $holidays;
    }

}
