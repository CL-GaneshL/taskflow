<?php

namespace App\Http\Controllers;

use App\TaskAllocation;
use Illuminate\Http\Request;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class TaskAllocationsController extends Controller {

    /**
     * 
     * @param Request $request
     * @return type
     */
    public function store(Request $request) {

        // ---------------------------------------------------
        \Log::debug('store : $request->all() = ' . $request->all());
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - create and store a new employee
        // ---------------------------------------------------
        $allocation = TaskAllocation::create($request->all());


        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'message' => 'Task allocation created succesfully',
                    'data' => $allocation
                        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {

        // ---------------------------------------------------
        // \Log::debug('destroy : $id = ' . $id);
        // ---------------------------------------------------
        TaskAllocation::destroy($id);

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'data' => $id,
                    'message' => 'Tasl deleted succesfully'
                        ], 200);
    }

}
