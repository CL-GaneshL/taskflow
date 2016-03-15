<?php

/*
  |--------------------------------------------------------------------------
  | Application Routes
  |--------------------------------------------------------------------------
  |
  | Here is where you can register all of the routes for an application.
  | It's a breeze. Simply tell Laravel the URIs it should respond to
  | and give it the controller to call when that URI is requested.
  |
 */

/*
  |--------------------------------------------------------------------------
  | Definitions
  |--------------------------------------------------------------------------
 */
define("ROOT_PATH", Config::get('constants.ROOT_PATH'));
define("UNDER_CONSTRUCTION_VIEW", Config::get('constants.UNDER_CONSTRUCTION_VIEW'));

define("CARATLANE_VIEW", Config::get('caratlane.constants.CARATLANE_VIEW'));
define("CARATLANE_TASKFLOW_PATH", Config::get('caratlane.constants.CARATLANE_TASKFLOW_PATH'));
define("CARATLANE_SIGNIN_PATH", Config::get('caratlane.constants.CARATLANE_SIGNIN_PATH'));
define("CARATLANE_FRAGMENTS_PATH", Config::get('caratlane.constants.CARATLANE_FRAGMENTS_PATH'));
define("CARATLANE_FRAGMENTS_VIEW_PREFIX", Config::get('caratlane.constants.CARATLANE_FRAGMENTS_VIEW_PREFIX'));
define("CARATLANE_APIS_V1_PATH", Config::get('caratlane.constants.CARATLANE_APIS_V1_PATH'));


/*
  |--------------------------------------------------------------------------
  | home page : http://[domain]
  |--------------------------------------------------------------------------
  |
  | Temporarily return the page "Under Construction".
  |
 */
Route::get(ROOT_PATH, function () {
    return view(UNDER_CONSTRUCTION_VIEW);
});

/*
  |==========================================================================
  | Caratlane routes
  |==========================================================================
 */

/*
  |--------------------------------------------------------------------------
  | Redirect to signin page
  |--------------------------------------------------------------------------
  | route : nuggetbox.app/taskflow
  |
 */
Route::get(CARATLANE_TASKFLOW_PATH, function () {
    return Redirect::to(CARATLANE_SIGNIN_PATH);
});

/*
  |--------------------------------------------------------------------------
  | taskflow's signin page
  |--------------------------------------------------------------------------
  | route : nuggetbox.gold/taskflow/signin
 */

Route::get(CARATLANE_SIGNIN_PATH, function () {
    return view(CARATLANE_VIEW);
});

/*
  |--------------------------------------------------------------------------
  | caratlane page fragments
  |--------------------------------------------------------------------------
  | routes : nuggetbox.app/taskflow/fragments/{fragname}
  | e.g. : nuggetbox.app/taskflow/fragments/app
 */
Route::group(['prefix' => CARATLANE_FRAGMENTS_PATH], function () {
    Route::get('{fragname}', function ($fragname) {
        return view(CARATLANE_FRAGMENTS_VIEW_PREFIX . '.' . $fragname);
    });
});

/*
  |--------------------------------------------------------------------------
  | taskflow API's routes
  |--------------------------------------------------------------------------
 */

Route::group(['prefix' => CARATLANE_APIS_V1_PATH], function() {

    // -------------------------------------------------------------------------------
    // - authentication routes
    // -------------------------------------------------------------------------------
    Route::resource('authenticate', 'AuthenticateController', ['only' => ['index']]);
    Route::post('authenticate', 'AuthenticateController@authenticate');
    Route::get('authenticate/user', 'AuthenticateController@getAuthenticatedUser');

    // -------------------------------------------------------------------------------
    // - API's routes
    // -------------------------------------------------------------------------------
    Route::resource('employees', 'EmployeesController');
    Route::resource('employees.tasks', 'EmployeesTasksController');
    Route::resource('employees.skills', 'EmployeesSkillsController');
    Route::resource('teams.employees', 'TeamsEmployeesController');
    Route::resource('teams', 'TeamsController');
    Route::resource('skills', 'SkillsController', ['only' => ['index', 'store', 'destroy']]);
    Route::resource('users', 'UsersController');
    Route::resource('templates', 'ProjectTemplatesController', ['only' => ['index', 'store', 'show', 'update', 'destroy']]);
    Route::resource('projects', 'ProjectsController', ['only' => ['index', 'store', 'show', 'update', 'destroy']]);
    Route::resource('nonworkingdays', 'NonWorkingDaysController', ['only' => ['index', 'store', 'update', 'destroy']]);
    Route::resource('holidays', 'HolidaysController', ['only' => ['show', 'store', 'update', 'destroy']]);
    Route::resource('projects-tasks', 'ProjectsTasksController', ['only' => ['index']]);
    Route::resource('task-allocations', 'TaskAllocationsController', ['only' => ['store', 'destroy']]);

    // -------------------------------------------------------------------------------
    // - allocation routes
    // -------------------------------------------------------------------------------
    Route::get('allocate', 'AllocateController@allocate');
    Route::get('allocate/reset', 'AllocateController@reset');
    Route::get('allocate/taskflow-configuration', 'AllocateController@getTaskflowConfiguration');
    Route::get('allocate/java-configuration', 'AllocateController@getJavaConfiguration');
});

/*
  |--------------------------------------------------------------------------
  | catch all routes
  |--------------------------------------------------------------------------
  |
  | all routes that are not home or api will be redirected to
  | the frontend This allows angular to route them.
  |
 */

