<?php

namespace App\Http\Controllers;

use Config;
use App\Project;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class ProjectsController extends Controller {

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

        // ---------------------------------------------------
        // - list of all projects
        //      -- open projects
        //      -- sorted by priority from high to low
        // ---------------------------------------------------

        $projects = ProjectsController::getAllOpenProjects();

        // ---------------------------------------------------
        // - list of all project templates available
        // ---------------------------------------------------
        $templates = ProjectTemplatesController::getAllTemplates();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => array($projects, $templates),
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

        $proposedReference = $request->reference;
        $reference = $proposedReference;

        $references = \DB::table('projects')
                ->select('projects.reference')
                ->where('projects.reference', 'LIKE', $proposedReference . '%')
                ->get();

        if (count($references) >= 1) {
            $reference = $proposedReference . '_' . (count($references) + 1);
        }

        // ---------------------------------------------------
        // - create and store a Project
        // ---------------------------------------------------
        $projectToCreate = [
            'reference' => $reference,
            'template_id' => $request->template_id,
            'priority' => $request->priority,
            'nb_products' => $request->nb_products,
            'start_date' => $request->start_date
        ];

        $newProject = Project::create($projectToCreate);

        $newProjectId = $newProject->id;

        $project = \DB::table('v_projects')
                ->select('v_projects.*')
                ->where('v_projects.id', '=', $newProjectId)
                ->get();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'data' => $project,
                    'message' => 'Template created succesfully'
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

        // ---------------------------------------------------
        // -update the project 
        // ---------------------------------------------------
        $project = Project::find($id);

        $project->reference = trim($request->reference) !== '' ? $request->reference : null;
        $project->template_id = trim($request->template_id) !== '' ? $request->template_id : null;
        $project->nb_products = trim($request->nb_products) !== '' ? $request->nb_products : null;
        $project->priority = trim($request->priority) !== '' ? $request->priority : null;
        $project->start_date = trim($request->start_date) !== '' ? $request->start_date : null;
        $project->end_date = trim($request->end_date) !== '' ? $request->end_date : null;

        // ---------------------------------------------------
        // \Log::debug('update : $project = ' . print_r($project, true));
        // ---------------------------------------------------

        $project->save();

        return response()->json([
                    'message' => 'Project updated succesfully',
                    'data' => $project
                        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($project_id) {

        \DB::table('projects')
                ->where('projects.id', '=', $project_id)
                ->update(array('open' => 0));

        $project = Project::find($project_id);

        return response()->json([
                    'message' => 'Template closed succesfully',
                    'data' => $project
                        ], 200);
    }

    /**
     * 
     * @return type
     */
    public static function getAllOpenProjects() {

        // v_projects is already ordered
        $projects = \DB::table('v_projects')
                ->select('v_projects.*')
                ->where('v_projects.open', '=', 1)
                ->get();

        if (empty($projects)) {
            $projects = array();
        }

        return $projects;
    }

}
