
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-xs-12">
        <h3 class="modal-title">New Employee</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-xs-12">

        <div class="row">
            <div class="col-md-5 col-lg-offset-1">

                <form class="form-horizontal padding-bottom-20" 
                      role="form"
                      name="employeeForm" >
                    <!-- ================================================== -->
                    <!-- - profile's fields                                 -->
                    <!-- ================================================== --> 
                    <div class="form-group">                    
                        <label class="control-label">
                            Employee Id
                        </label>
                        <input type="text"
                               class="form-control"
                               name="employeeId" 
                               ng-model="employee.employeeId"
                               ng-value ="employee.employeeId" />             
                    </div>

                    <div class="form-group">                    
                        <label class="control-label">
                            Full Name
                        </label>
                        <input type="text"
                               class="form-control"
                               name="fullName" 
                               ng-model="employee.fullName"
                               ng-value ="employee.fullName" />             
                    </div>

                    <div class="form-group">                    
                        <label class="control-label">
                            First Name
                        </label>
                        <input type="text"
                               class="form-control"
                               name="firstname" 
                               ng-model="employee.firstName"
                               ng-value ="employee.firstName" />             
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            Last Name
                        </label>
                        <input type="text"
                               class="form-control" 
                               name="lastname" 
                               ng-model="employee.lastName"
                               ng-value ="employee.lastName" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            Email Address
                        </label>
                        <input type="email" 
                               class="form-control" 
                               name="email"
                               ng-model="employee.email"
                               ng-value ="employee.email" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            Phone
                        </label>
                        <input type="text"
                               class="form-control" 
                               name="phone"
                               ng-model="employee.phone"
                               ng-value ="employee.phone" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            Location
                        </label>
                        <input type="text" 
                               class="form-control"
                               name="url"
                               ng-model="employee.location"
                               ng-value ="employee.location" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">
                            Employement
                        </label>
                        <select class="form-control cs-select cs-skin-elastic" 
                                ng-model="employee.employementType">
                            <option value="<%employee.employementType%>" disabled selected><%employee.employementType%></option>
                            <option value="Intern">Intern</option>
                            <option value="FTE">FTE</option>
                        </select>
                    </div> 

            </div>

            <!-- ================================================== -->
            <!-- - image loader                                     -->
            <!-- ================================================== --> 
            <div class="col-md-5 pull-right">

                <div class="form-group">
                    <label>
                        Image Upload
                    </label>
                    <div flow-init flow-object="obj.flow">
                        <div class="user-image">
                            <div class="thumbnail margin-bottom-5">
                                <img src="{{ asset('../../img/caratlane/avatar-150.png')}}"
                                     alt="" ng-if="!obj.flow.files.length && noImage">
                                <img ng-src="<%employee.avatar%>" 
                                     alt="" ng-if="!obj.flow.files.length && !noImage">
                                <img flow-img="obj.flow.files[0]" 
                                     ng-if="obj.flow.files.length">
                            </div>
                        </div>
                        <div class="user-image-buttons-edit" ng-if="obj.flow.files.length">
                            <span class="btn btn-primary" flow-btn><i class="fa fa-pencil"></i></span>
                            <span class="btn btn-danger" ng-click="obj.flow.cancel()"> <i class="fa fa-times"></i></span>
                        </div>
                        <div class="user-image-buttons-edit" ng-if="!obj.flow.files.length">
                            <span class="btn btn-primary" flow-btn><i class="fa fa-pencil"></i></span>
                            <span class="btn btn-danger" ng-if="!noImage" ng-click="removeImage()"><i class="fa fa-times"></i> </span>
                        </div>
                    </div>
                </div>

                <!-- ================================================== -->
                <hr>
                <!-- ================================================== -->

                <!--------------------------------------------------------->
                <!-- type of event radio buttons                         -->
                <!--------------------------------------------------------->
                <div class="form-group">

                    <div class="row col-xs-12" ng-init="manager">

                        <div class='row'>
                            <div class="col-md-12">
                                <input type="radio"
                                       ng-model="manager"
                                       id="isProjectManager" 
                                       name="isProjectManager" 
                                       value="projectManager" 
                                       />
                                <label for="isProjectManager">
                                    Project Manager
                                </label>
                            </div>
                        </div>

                        <div class='row'>
                            <div class="col-md-12">
                                <input type="radio"
                                       ng-model="manager"
                                       id="isTeamLeader" 
                                       name="isTeamLeader" 
                                       value="teamLeader" 
                                       />
                                <label for="isTeamLeader">
                                    Team Leader
                                </label>
                            </div>
                        </div>

                        <div class='row'>
                            <div class="col-md-12">
                                <input type="radio"
                                       ng-model="manager"
                                       id="isTeamMember" 
                                       name="isTeamMember" 
                                       value="teamMember" 
                                       />
                                <label for="isTeamMember">
                                    Team Member
                                </label>
                            </div>
                        </div>

                    </div>

                </div>

            </div>

            <!-- ================================================== -->
        </div>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-xs-12">

        <div class="row form-group">

            <button class="btn btn-primary btn-o" ng-click="cancel()">
                Cancel
            </button>

            <button class="btn btn-danger btn-o" ng-click="createEmployee()">
                Create
            </button>

        </div>

    </div>

</div>
<!-- end: PROFILE -->
