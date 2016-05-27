
<!-- ================================================== -->
<!-- authenticated user role and privileges             -->
<!-- ================================================== -->

@inject('authUser', 'App\Helpers\AuthenticatedUser')


<!-- ================================================== -->
<!-- profile Skill tab                                  -->
<!-- ================================================== -->
<div ng-controller="profileSkillsCtrl">

    <div class="container-fluid container-fullw bg-white">
        <div class="row">
            <div class="col-md-10 col-lg-offset-1">



                <!-- ================================================== -->
                <!-- - skills table                                     -->
                <!-- ================================================== -->                    
                <table class="table table-condensed table-hover">
                    <thead>
                        <tr>
                            <th class="hidden-xs col-md-2">Reference</th>    
                            <th class="col-md-2 text-center">Duration</th>
                            <th class="col-md-7">Designation</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr ng-repeat="skill in skills">

                            <td class="hidden-xs col-md-2"> <% skill.reference %> </td>
                            <td class="col-md-2 text-center"> <% skill.duration %> </td>
                            <td class="col-md-7"> <% skill.designation %> </td>

                            <!-- --------------------------------------------------- --> 
                            <!-- - remove skill button                               -->
                            <!-- --------------------------------------------------- --> 
                            @if ( $authUser->hasPrivilege('REMOVE_SKILL_PRIVILEGE') )

                            <td class="hidden-xs col-md-3">
                                <button type="button" 
                                        class="btn btn-o btn-light-yellow btn-xs pull-right"
                                        ng-click="removeSkill( <% skill.id %> )"
                                        >
                                    <span>Remove</span>
                                </button>
                            </td>

                            @endif
                            <!-- --------------------------------------------------- --> 

                        </tr>

                    </tbody>
                </table>

                <div class="row col-md-12 text-muted text-small"
                     ng-show="skills.length === 0"
                     >
                    <p>No skills attached !</p>
                </div>

                <!-- ================================================== -->
                <!-- - add a new skill area                             -->
                <!-- ================================================== -->         
                @if ( $authUser->hasPrivilege('ADD_SKILL_PRIVILEGE') )

                <form class="form-horizontal padding-top-30 padding-bottom-20" 
                      role="form"
                      name="newSkillForm" >
                    <fieldset>

                        <legend>
                            Add a new Skill
                        </legend>

                        <div class="form-group">
                            <select class="form-control cs-select cs-skin-elastic" 
                                    ng-model="addedSkill"
                                    ng-options="skill.designation for skill in allSkills"
                                    >
                                <option value="" disabled="">Select a Skill</option>
                            </select>
                        </div> 

                        <!-- --------------------------------------------------- --> 
                        <!-- - new skill button                                  -->
                        <!-- --------------------------------------------------- --> 
                        <div class="form-group">
                            <!--<div class="col-md-8">-->
                            <div class="push-right">
                                <input type="submit"
                                       value="Add a Skill"
                                       class="btn btn-primary btn-o pull-right"
                                       ng-click="addSkill()"
                                       />
                            </div>
                        </div>

                    </fieldset>
                </form>

                @endif
                <!-- ================================================== -->


            </div>
        </div>

    </div>
</div>