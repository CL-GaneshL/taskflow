
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Teams</h1>
        </div>
    </div>
</section>

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="container-fluid container-fullw bg-white">        

    <div ng-controller="teamsCtrl">

        <div class="row col-md-12">

            <div class="col-md-4">

                <!-- ================================================== -->
                <!-- - new team form                                   -->
                <!-- ================================================== -->
                <div class="col-md-10 col-lg-offset-1">

                    <form class="form-horizontal padding-bottom-20" 
                          role="form"
                          name="teamForm" >

                        <fieldset>

                            <legend>
                                Create a new Team
                            </legend>

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Team Name :
                                </label>

                                <div class="col-md-8">

                                    <input type="text" 
                                           class="form-control"
                                           name="reference"
                                           ng-model="newTeamName"
                                           ng-value ="profile.location"
                                           placeholder="Enter team name"
                                           ng-trim="true"
                                           required
                                           />
                                </div>
                            </div>

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Team Leader :
                                </label>

                                <div class="col-md-8">

                                    <select class="form-control cs-select cs-skin-elastic" 
                                            ng-model="newTeamLeader"
                                            ng-app=""
                                            ng-options="team.fullName for team in teamLeaders" >
                                        <option value="" disabled="">Select</option>
                                    </select>

                                </div> 

                            </div> 

                            <!-- ================================================== -->
                            <!-- - new team button                                 -->
                            <!-- ================================================== -->
                            <div class="form-group">

                                <div class="col-md-offset-7 col-md-4 text-right">

                                    <input type="submit"
                                           value="New Team"
                                           class="btn btn-primary btn-o pull-right"
                                           data-ng-click="createTeam()" />

                                </div>

                            </div>

                        </fieldset>
                    </form> 

                </div>
                <!-- ================================================== -->

            </div>

            <!-- ================================================== -->
            <!-- - skills table                                     -->
            <!-- ================================================== -->                    
            <div class="col-md-8">

                <table class="table table-condensed table-striped table-hover">

                    <thead>
                        <tr>
                            <th class="hidden-xs col-md-4">Team Name</th>
                            <th><i class="fa fa-time"></i>Team Leader</th>
                            <th class="hidden-xs col-md-4"></th>
                        </tr>
                    </thead>

                    <tbody>

                        <tr ng-repeat="team in teams"  >

                            <td class="col-md-4"> <% team.teamName %> </td>
                            <td class="hidden-xs col-md-5"> <% team.teamLeaderFullName %> </td>

                            <!-- ================================================== -->
                            <!-- - edit and remove icons                            -->
                            <!-- ================================================== -->  
                            <td class="col-md-3">
                                <div class="visible-md visible-lg hidden-sm hidden-xs">

                                    <a href="#"
                                       class="btn btn-transparent btn-xs" 
                                       tooltip-placement="top" 
                                       uib-tooltip="Edit Team"
                                       ng-click="editTeam( <% team.id %> )" >
                                        <i class="fa fa-pencil"></i>
                                    </a>

                                    <a href="#"
                                       class="btn btn-transparent btn-xs tooltips" 
                                       tooltip-placement="top"
                                       uib-tooltip="Delete Team"
                                       ng-click="deleteTeam( <% team.id %> )" >
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
                                                   ng-click="editTeam( <% team.id %> )" >
                                                    Edit
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" 
                                                   ng-click="deleteTeam( <% team.id %> )" >
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
                     ng-show="teams.length === 0"
                     >
                    <p>No team defined !</p>
                </div>
                
            </div>
        </div>
    </div>

</div>

