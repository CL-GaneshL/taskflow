<?php

namespace App\Http\Controllers;

use Config;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class MetricsController extends Controller {

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
        
    }

    /**
     * Retrieve all information about an employee
     *
     * @param  int  $id the employee id
     * @return \Illuminate\Http\Response
     * 
     */
    public function show($project_id) {
        
    }

    /**
     * 
     * @param Request $request
     */
    public function getEmployeesMetrics(Request $request) {

        $id = $request->reference;
        $start_date = $request->start_date;
        $end_date = $request->end_date;

        // ---------------------------------------------------
        \Log::debug('$id : ' . $id);
        \Log::debug('$start_date : ' . $start_date);
        \Log::debug('$end_date : ' . $end_date);
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - get the diagram's labels
        // ---------------------------------------------------
        $labels = array();
        $data = \DB::select("CALL getLabels2($start_date, $end_date)");

        foreach ($data as $object) {
            $arr = (array) $object;
            $labels[] = $arr['label'];
        }

        // ---------------------------------------------------
        // - get the Planned Value (PV) data
        // ---------------------------------------------------
        $PV = array();
        $data = \DB::select("CALL getEmployeePV($start_date, $end_date)");

        foreach ($data as $object) {
            $arr = (array) $object;
            $PV[] = $arr['PVi'];
        }

        // ---------------------------------------------------
        // - get the Earned Value (EV) data.
        // ---------------------------------------------------
        $EV = array();
        $data = \DB::select("CALL getEmployeeEV($start_date, $end_date)");

        foreach ($data as $object) {
            $arr = (array) $object;
            $EV[] = $arr['EVi'];
        }

        // ---------------------------------------------------
        // - create the response 
        // ---------------------------------------------------
        $status = array($labels, $PV, $EV);

        // ---------------------------------------------------
        \Log::debug('metrics : $status = ' . print_r($status, true));
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - the response
        // ---------------------------------------------------

        return response()->json(
                        [
                    'data' => $status,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * 
     * @param type $project_id
     */
    public function getProjectStatus($project_id) {

        // ---------------------------------------------------
        // - get the diagram's labels
        // ---------------------------------------------------
        $labels = array();
        $data = \DB::select("CALL getLabels(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $labels[] = $arr['label'];
        }

        // ---------------------------------------------------
        // number of hours planned against time        
        // ---------------------------------------------------
        $HP = array();
        $data = \DB::select("CALL getHP(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $HP[] = $arr['HPi'];
        }

        // ---------------------------------------------------
        // number of hours consumed against time
        // ---------------------------------------------------
        $HC = array();
        $data = \DB::select("CALL getHC(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $HC[] = $arr['HCi'];
        }

        // ---------------------------------------------------
        // number of products planned
        // ---------------------------------------------------
        $PP = array();
        $data = \DB::select("CALL getPP(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $PP[] = $arr['PPi'];
        }

        // ---------------------------------------------------
        // number of products completed
        // ---------------------------------------------------
        $PC = array();
        $data = \DB::select("CALL getPC(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $PC[] = $arr['PCi'];
        }

        // ---------------------------------------------------
        // - create the response 
        // ---------------------------------------------------
        $status = array($labels, $HP, $HC, $PP, $PC);

        // ---------------------------------------------------
        // \Log::debug('metrics : $status = ' . print_r($status, true));
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - the response
        // ---------------------------------------------------

        return response()->json(
                        [
                    'data' => $status,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * 
     * @param type $project_id
     * @return type
     */
    public function getProjectsMetrics($project_id) {

        // ---------------------------------------------------
        // - get the diagram's labels
        // ---------------------------------------------------
        $labels = array();
        $data = \DB::select("CALL getLabels(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $labels[] = $arr['label'];
        }

        // ---------------------------------------------------
        // - get the Planned Value (PV) data
        // ---------------------------------------------------
        $PV = array();
        $data = \DB::select("CALL getPV(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $PV[] = $arr['PVi'];
        }

        // ---------------------------------------------------
        // - get the Earned Value (EV) data.
        // ---------------------------------------------------
        $EV = array();
        $data = \DB::select("CALL getEV(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $EV[] = $arr['EVi'];
        }

        // ---------------------------------------------------
        // - get the Cost Performance Index (CPI) data.
        // ---------------------------------------------------
        $CPI = array();
        $data = \DB::select("CALL getCPI(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $CPI[] = $arr['CPIi'];
        }

        // ---------------------------------------------------
        // - get the Schedule Performance Index (SPI) data.
        // ---------------------------------------------------
        $SPI = array();
        $data = \DB::select("CALL getSPI(" . $project_id . ")");

        foreach ($data as $object) {
            $arr = (array) $object;
            $SPI[] = $arr['SPIi'];
        }

        // ---------------------------------------------------
        // - get today's indicators.
        // ---------------------------------------------------

        $data = \DB::select("CALL getIndicators(" . $project_id . ")");

        $indicators = array();
        if (sizeof($data) > 0) {
            $arr = (array) $data[0];
            $indicators['PVi'] = $arr['PVi'];
            $indicators['EVi'] = $arr['EVi'];
            $indicators['CPIi'] = $arr['CPIi'];
            $indicators['SPIi'] = $arr['SPIi'];
        }

        // ---------------------------------------------------
        // - create the response 
        // ---------------------------------------------------
        $metrics = array($labels, $PV, $EV, $CPI, $SPI, $indicators);

        // ---------------------------------------------------
        // \Log::debug('metrics : $metrics = ' . print_r($metrics, true));
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - the response
        // ---------------------------------------------------

        return response()->json(
                        [
                    'data' => $metrics,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
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
        
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {
        
    }

}
