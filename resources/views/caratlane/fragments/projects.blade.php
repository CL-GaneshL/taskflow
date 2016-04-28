
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Projects</h1>
        </div>

    </div>
</section>

<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<div class="container-fluid container-fullw bg-white no-border">

    <div ng-controller="projectsCtrl"> 

        <div class="row col-md-12">

            <div class="col-md-5">

                <!-- ================================================== -->
                <!-- - new project form                                 -->
                <!-- ================================================== -->
                <div class="col-md-12">

                    <form class="form-horizontal padding-bottom-20" 
                          role="form"
                          name="projectForm" >

                        <fieldset>

                            <legend>
                                Create a Project
                            </legend>

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Template :
                                </label>

                                <div class="col-md-8">

                                    <select class="form-control cs-select cs-skin-elastic" 
                                            ng-model="toCreateProject.template"
                                            ng-app=""
                                            ng-options="template.reference for template in templates" >
                                        <option value="" disabled="">Select Template</option>
                                    </select>

                                </div> 

                            </div>    

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Start Date :
                                </label>                           

                                <div class="col-md-8">

                                    <div class="input-group">

                                        <span class="input-group-btn">
                                            <button type="button" 
                                                    class="btn btn-default"
                                                    ng-click="open($event)"
                                                    >
                                                <i class="glyphicon glyphicon-calendar"></i>
                                            </button> 
                                        </span>

                                        <input type="text" 
                                               class="form-control" 
                                               uib-datepicker-popup="<%format%>" 
                                               ng-model="toCreateProject.start_date" 
                                               is-open="opened" 
                                               min-date="minDate"
                                               max-date="maxDate" 
                                               datepicker-options="dateOptions"
                                               date-disabled="disabled(date, mode)" 
                                               ng-required="true" 
                                               close-text="Close" 
                                               />

                                    </div>

                                </div>                     

                            </div>   


                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Nb Products :
                                </label>

                                <div class="col-md-8">

                                    <input type="text" 
                                           class="form-control"
                                           ng-model="toCreateProject.nb_products"
                                           placeholder="Enter Nb Products"
                                           ng-trim="true"
                                           required
                                           />
                                </div>
                            </div>

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Priority :
                                </label>

                                <div class="col-md-8">

                                    <select class="form-control cs-select cs-skin-elastic" 
                                            ng-model="toCreateProject.priority"  >
                                        <option value="" disabled selected>Select Priority</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9">9</option>
                                        <option value="10">10</option>
                                    </select>

                                </div> 

                            </div>  

                            <!-- ================================================== -->
                            <!-- - create project button                            -->
                            <!-- ================================================== -->
                            <div class="form-group">

                                <div class="col-md-offset-7 col-md-4 text-right">

                                    <input type="submit"
                                           value="New Project"
                                           class="btn btn-primary btn-o pull-right"
                                           data-ng-click="createProject()" />

                                </div>

                            </div>

                        </fieldset>
                    </form> 

                </div>
                <!-- ================================================== -->

            </div>

            <!-- ================================================== -->
            <!-- - Projects table                                     -->
            <!-- ================================================== -->                    
            <div class="col-md-7">

                <table class="table table-condensed table-striped table-hover">

                    <thead>
                        <tr>
                            <th class="col-md-4">Reference</th>
                            <th class="col-md-1 text-center">PRY</th>
                            <th class="col-md-1 text-center">NBP</th>
                            <th class="col-md-2 text-center">Start</th>
                            <th class="col-md-2 text-center">End</th>
                            <td>
                        </tr>
                    </thead>

                    <tbody>

                        <tr ng-repeat="project in projects"  >

                            <td class="col-md-4"> <% project.reference %> </td>
                            <td class="col-md-1 text-center"> <% project.priority %> </td>
                            <td class="col-md-1 text-center"> <% project.nb_products %> </td>
                            <td class="col-md-2 text-center"> <% project.start_date %> </td>
                            <td class="col-md-2 text-center"> <% project.end_date %> </td>

                            <!-- ================================================== -->
                            <!-- - edit and remove icons                            -->
                            <!-- ================================================== -->  
                            <td class="col-md-2">
                                <div class="visible-md visible-lg hidden-sm hidden-xs">

                                    <a href="#"
                                       class="btn btn-transparent btn-xs" 
                                       tooltip-placement="top" 
                                       uib-tooltip="Edit Project"
                                       ng-click="updateProject( <% project.id %> )" >
                                        <i class="fa fa-pencil"></i>
                                    </a>
                                    
                                    <a href="#"
                                       class="btn btn-transparent btn-xs tooltips" 
                                       tooltip-placement="top"
                                       uib-tooltip="Close Project"
                                       ng-click="closeProject( <% project.id %> )" >
                                        <i class="fa fa-times fa fa-white"></i>
                                    </a>
                                                                                                            
                                </div>

                                <div class="visible-xs visible-sm hidden-md hidden-lg">
                                    <div class="btn-group" uib-dropdown>

                                        <a href="#" type="button"
                                           class="btn btn-primary btn-sm dropdown-toggle"
                                           uib-dropdown-toggle>
                                            <i class="fa fa-cog"></i>&nbsp;<span class="caret"></span>
                                        </a>

                                        <ul class="dropdown-menu pull-right dropdown-light" role="menu">
                                            <li>
                                                <a href="#"  
                                                   ng-click="updateProject( <% project . id %> )" >
                                                    Edit
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" 
                                                   ng-click="closeProject( <% project.id %> )" >
                                                    Remove
                                                </a>
                                            </li>
                                        </ul>

                                    </div>
                                </div>

                            </td>
                            <!-- ================================================== -->

                        </tr>

                    </tbody>
                </table>
                
                <div class="row col-md-12 text-muted text-small"
                     ng-show="projects.length === 0"
                     >
                    <p>No open projects !</p>
                </div>
                
            </div>
        </div>
    </div>

</div>

<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->
