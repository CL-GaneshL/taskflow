
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Edit Template</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <!-- ================================================== -->
        <!-- - new Template form                                -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="templateForm" >

            <div class="form-group">
                <label class="control-label">
                    Reference :
                </label>
                <input type="text" 
                       class="form-control"
                       name="toEditTemplateReference"
                       ng-value ="toEditTemplateReference"
                       ng-readonly="true"
                       disabled=""
                       />
            </div>

            <div class="form-group">
                <label class="control-label">
                    Designation :
                </label>
                <input type="text" 
                       class="form-control"
                       name="toEditTemplateDesignation"
                       ng-value ="toEditTemplateDesignation"
                       ng-readonly="true"
                       disabled=""
                       />
            </div>

            <!-- ================================================== -->

            <div class="row padding-top-15">

                <fieldset>

                    <legend>
                        Skills
                    </legend>

                    <!-- ================================================== -->
                    <!-- - skills table                                     -->
                    <!-- ================================================== -->                    
                    <table class="table table-condensed table-striped table-hover">
                        <thead>
                            <tr>
                                <th class="hidden-xs col-md-2">Ref</th>
                                <th><i class="fa fa-time"></i>Designation</th>
                                <th class="hidden-xs col-md-4"></th>
                            </tr>
                        </thead>
                        <tbody>

                            <tr ng-repeat="skill in skills">

                                <td class="col-md-2"> <% skill.reference %> </td>
                                <td class="hidden-xs col-md-7"> <% skill.designation %> </td>
                                <td class="hidden-xs col-md-3">

                                    <button type="button" 
                                            class="btn btn-o btn-light-yellow btn-xs pull-right"
                                            ng-click="removeSkill( <% skill.id %> )"
                                            >
                                        <span>Remove</span>
                                    </button>

                                </td>
                            </tr>

                        </tbody>
                    </table>

                    <!-- ================================================== -->
                    <!-- - Skill select dropdown menu                       -->
                    <!-- ================================================== -->
                    <div class="form-group padding-15">
                        <label class="control-label">
                            New Skill :
                        </label>
                        <select class="form-control cs-select cs-skin-elastic" 
                                ng-model="newSkill"
                                ng-options="skill.designation for skill in selectionSkills" 
                                >
                            <option value="" disabled="">Select</option>
                        </select>
                    </div> 

                    <!-- ================================================== -->
                    <!-- - add a new skill button                           -->
                    <!-- ================================================== -->
                    <div class="form-group padding-15">
                        <input type="submit"
                               value="Add Skill"
                               class="btn btn-primary btn-o pull-right"
                               ng-click="addSkill()"
                               />
                    </div>


                </fieldset>

            </div>

        </form>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="save()">
            Save
        </button>

    </div>

</div>