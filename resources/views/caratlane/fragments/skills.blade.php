
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Skills</h1>
        </div>

    </div>

</section>


<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="container-fluid container-fullw bg-white">        

    <div ng-controller="skillsCtrl">

        <div class="row col-md-12">

            <div class="col-md-4">

                <!-- ================================================== -->
                <!-- - new skill form                                   -->
                <!-- ================================================== -->
                <div class="col-md-10 col-lg-offset-1">

                    <form class="form-horizontal padding-bottom-20" 
                          role="form"
                          name="skillForm" >

                        <fieldset>

                            <legend>
                                Create a new Skill
                            </legend>

                            <!-- ================================================== -->
                            <!-- - reference field                                 -->
                            <!-- ================================================== -->
                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Reference :
                                </label>

                                <div class="col-md-8">

                                    <input type="text"
                                           class="form-control"
                                           name="reference" 
                                           id="reference"
                                           placeholder="Enter reference"
                                           required
                                           ng-model="newSkillReference"
                                           ng-trim="true" 
                                           required />

                                </div>
                            </div>

                            <!-- ================================================== -->
                            <!-- - designation field                                 -->
                            <!-- ================================================== -->
                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Designation :
                                </label>

                                <div class="col-md-8">

                                    <input type="text" 
                                           class="form-control"
                                           name="designation"
                                           id="designation"
                                           placeholder="Enter designation"
                                           ng-model="newSkillDesignation" 
                                           ng-trim="true" 
                                           required />

                                </div>

                            </div>

                            <!-- ================================================== -->
                            <!-- - designation field                                 -->
                            <!-- ================================================== -->
                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Duration :
                                </label>

                                <div class="col-md-8">
                                    <select class="form-control cs-select cs-skin-elastic" 
                                            ng-model="newSkillDuration"
                                            ng-app=""
                                            ng-options="duration for duration in duration_choices" 
                                            required >
                                        <option value="" disabled=""><% duration %></option>
                                    </select>
                                </div>

                            </div>

                            <!-- ================================================== -->
                            <!-- - new skill button                                 -->
                            <!-- ================================================== -->
                            <div class="form-group">
                                <div class="col-md-offset-7 col-md-4 text-right">
                                    <input type="submit"
                                           value="New Skill"
                                           class="btn btn-primary btn-o pull-right"
                                           data-ng-click="createSkill()" />
                                </div>
                            </div>

                    </form>
                    </fieldset>
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
                            <th class="hidden-xs col-md-2">Reference</th>    
                            <th class="col-md-2 text-center">Duration</th>
                            <th>Designation</th>
                        </tr>
                    </thead>

                    <tbody>

                        <tr ng-repeat="skill in skills"  >

                            <td class="hidden-xs col-md-2"> <% skill.reference %> </td>
                            <td class="col-md-2 text-center"> <% skill.duration %> </td>
                            <td class="col-md-7"> <% skill.designation %> </td>

                            <!-- ================================================== -->
                            <!-- - edit and remove icons                            -->
                            <!-- ================================================== -->  
                            <td class="col-md-1">
                                <div class="visible-md visible-lg hidden-sm hidden-xs">

                                    <a href="#"
                                       class="btn btn-transparent btn-xs tooltips" 
                                       tooltip-placement="top"
                                       uib-tooltip="Close Skill"
                                       ng-click="closeSkill( <% skill.id %> )" >
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
                                                   ng-click="closeSkill( <% skill.id %> )" >
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
                     ng-show="skills.length === 0"
                     >
                    <p>No skills defined !</p>
                </div>


            </div>
        </div>
    </div>

</div>


