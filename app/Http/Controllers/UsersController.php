<?php

namespace App\Http\Controllers;

use Hash;
use Config;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class UsersController extends Controller {

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
    public function show($id) {

        $responseData = array();

        $user = \DB::table('users')
                ->select('*')
                ->where('employeeId', '=', $id)
                ->get();

        // transfer it into the response array
        array_push($responseData, $user);

        // get all employee's data from the Employees controller
        $employeeData = EmployeesController::getEmployeeData($id);

        // transfer it into the response array
        foreach ($employeeData as $data) {
            array_push($responseData, $data);
        }

        return response()->json(
                        [
                    'data' => $responseData,
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
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $employee_id) {

        $password = $request->password;

        // -------------------------------------
        // update the password for the user
        // with that employee id.
        // -------------------------------------
        \DB::table('users')
                ->where('employeeId', '=', $employee_id)
                ->update(array('password' => Hash::make($password)));

        // -------------------------------------
        // response
        // -------------------------------------
        return response()->json(
                        [
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {
        //
    }

}
