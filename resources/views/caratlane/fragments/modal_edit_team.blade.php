
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Edit Team</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <!-- ================================================== -->
        <!-- - new team form                                    -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="teamForm" >

            <div class="form-group">
                <label class="control-label">
                    Team Name :
                </label>
                <input type="text" 
                       class="form-control"
                       name="toUpdateTeamName"
                       ng-value ="toUpdateTeamName"
                       ng-readonly="true"
                           disabled=""
                       />
            </div>
            
             <div class="form-group">
                <label class="control-label">
                    Team Leader :
                </label>
                <input type="text" 
                       class="form-control"
                       name="toUpdateTeamLeaderFullName"
                       ng-value ="toUpdateTeamLeaderFullName"
                       ng-readonly="true"
                           disabled=""
                       />
            </div>

            <!-- ================================================== -->

            <div class="row padding-top-15">

                <!-- ================================================== -->
                <!-- - skills table                                     -->
                <!-- ================================================== -->                    
                <table class="table table-condensed table-striped table-hover">
                    <thead>
                        <tr>
                            <th class="hidden-xs col-md-2">Id</th>
                            <th><i class="fa fa-time"></i>Full Name</th>
                            <th class="hidden-xs col-md-4"></th>
                        </tr>
                    </thead>
                    <tbody>

                        <tr ng-repeat="member in members">

                            <td class="col-md-2"> <% member.employeeId %> </td>
                            <td class="hidden-xs col-md-7"> <% member.fullName %> </td>
                            <td class="hidden-xs col-md-3">

                                <button type="button" 
                                        class="btn btn-o btn-light-yellow btn-xs pull-right"
                                        ng-click="removeEmployee( <% member.id %> )"
                                        >
                                    <span>Remove</span>
                                </button>

                            </td>
                        </tr>

                    </tbody>
                </table>

                <!-- ================================================== -->
                <!-- - add a new team select dropdown menu              -->
                <!-- ================================================== -->
                <div class="form-group">
                    <label class="control-label">
                        New Employee :
                    </label>
                    <select class="form-control cs-select cs-skin-elastic" 
                            ng-model="newEmployee"
                            ng-options="employee.fullName for employee in allEMployees" 
                            >
                        <option value="" disabled="">Select</option>
                    </select>
                </div> 

                <!-- ================================================== -->
                <!-- - add a new skill button                           -->
                <!-- ================================================== -->
                <div class="form-group">
                    <input type="submit"
                           value="Add an Employee"
                           class="btn btn-primary btn-o pull-right"
                           ng-click="addEmployee()"
                           />
                </div>

            </div>
        </form>
  
    </div>
    
    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Done
        </button>

    </div>

</div>