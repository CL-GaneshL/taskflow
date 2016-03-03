
<!-- ============================================================= -->
<!-- -- authenticated user role and privileges                     -->
<!-- ============================================================= -->

@inject('authUser', 'App\Helpers\AuthenticatedUser')


<!-- ============================================================= -->
<!-- -- profile Edit tab                                          -->
<!-- ============================================================= -->
<div ng-controller="profileEditCtrl">

    <div class="row">
        <div class="col-md-5 col-lg-offset-1">

            <form class="form-horizontal padding-bottom-20" 
                  role="form"
                  name="employeeForm" >
                <!-- -- ================================================== -->
                <!-- -- - profile's fields                                 -->
                <!-- -- ================================================== --> 
                <div class="form-group">                    
                    <label class="control-label">
                        Employee Id
                    </label>
                    <input type="text"
                           class="form-control"
                           name="employeeId" 
                           ng-model="profile.employeeId"
                           ng-value ="profile.employeeId"
                           ng-readonly="true"
                           disabled=""/>             
                </div>

                <div class="form-group">                    
                    <label class="control-label">
                        Full Name
                    </label>
                    <input type="text"
                           class="form-control"
                           name="fullName" 
                           ng-model="profile.fullName"
                           ng-value ="profile.fullName" />             
                </div>

                <div class="form-group">                    
                    <label class="control-label">
                        First Name
                    </label>
                    <input type="text"
                           class="form-control"
                           name="firstname" 
                           ng-model="profile.firstName"
                           ng-value ="profile.firstName" />             
                </div>

                <div class="form-group">
                    <label class="control-label">
                        Last Name
                    </label>
                    <input type="text"
                           class="form-control" 
                           name="lastname" 
                           ng-model="profile.lastName"
                           ng-value ="profile.lastName" />
                </div>

                <div class="form-group">
                    <label class="control-label">
                        Email Address
                    </label>
                    <input type="email" 
                           class="form-control" 
                           name="email"
                           ng-model="profile.email"
                           ng-value ="profile.email" />
                </div>

                <div class="form-group">
                    <label class="control-label">
                        Phone
                    </label>
                    <input type="text"
                           class="form-control" 
                           name="phone"
                           ng-model="profile.phone"
                           ng-value ="profile.phone" />
                </div>

                <div class="form-group">
                    <label class="control-label">
                        Location
                    </label>
                    <input type="text" 
                           class="form-control"
                           name="url"
                           ng-model="profile.location"
                           ng-value ="profile.location" />
                </div>

                <!-- -- --------------------------------------------------- --> 
                <!-- -- - editable only by Project Manager                  -->
                <!-- -- --------------------------------------------------- --> 
                @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

                <div class="form-group">
                    <label class="control-label">
                        Employement
                    </label>
                    <select class="form-control cs-select cs-skin-elastic" 
                            ng-model="profile.employementType">
                        <option value="<%profile.employementType%>" disabled selected>
                            <%profile.employementType%>
                        </option>
                        <option value="Intern">Intern</option>
                        <option value="FTE">FTE</option>
                    </select>
                </div> 

                <div class="form-group">
                    <label class="control-label">
                        Productivity (hours)
                    </label>
                    <input type="text" 
                           class="form-control"
                           name="productivity"
                           ng-model="profile.productivity"
                           ng-value ="profile.productivity" />
                </div>

                <!-- -- --------------------------------------------------- --> 
                @else
                <!-- -- --------------------------------------------------- --> 

                <div class="form-group">
                    <label class="control-label">
                        Employement
                    </label>
                    <input type="text" 
                           class="form-control"
                           name="employementType"
                           ng-model="profile.employementType"
                           ng-value ="profile.employementType"
                           ng-readonly="true"
                           disabled=""/>
                </div>

                <div class="form-group">
                    <label class="control-label">
                        Productivity (hours)
                    </label>
                    <input type="text" 
                           class="form-control"
                           name="productivity"
                           ng-model="profile.productivity"
                           ng-value ="profile.productivity"
                           ng-readonly="true"
                           disabled=""/>
                </div>

                @endif
                <!-- -- --------------------------------------------------- --> 

        </div>

        <!-- -- ================================================== -->
        <!-- -- - image loader                                     -->
        <!-- -- ================================================== --> 
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
                            <img ng-src="<%profile.avatar%>" 
                                 alt="" ng-if="!obj.flow.files.length && !noImage">
                            <img flow-img="obj.flow.files[0]" 
                                 ng-if="obj.flow.files.length">
                        </div>
                    </div>
                    <div class="user-image-buttons-edit" ng-if="obj.flow.files.length">
                        <span class="btn btn-primary" flow-btn>
                            <i class="fa fa-pencil"></i>
                        </span>
                        <span class="btn btn-danger" ng-click="obj.flow.cancel()"> 
                            <i class="fa fa-times"></i>
                        </span>
                    </div>
                    <div class="user-image-buttons-edit" ng-if="!obj.flow.files.length">
                        <span class="btn btn-primary" flow-btn>
                            <i class="fa fa-pencil"></i>
                        </span>
                        <span class="btn btn-danger" ng-if="!noImage" ng-click="removeImage()">
                            <i class="fa fa-times"></i>
                        </span>
                    </div>
                </div>
            </div>

            <!-- -- ================================================== -->
            <hr>
            <!-- -- ================================================== -->

            <!-- --------------------------------------------------------->
            <!-- -- type of event radio buttons                         -->
            <!-- -- - editable only by Project Manager                  -->
            <!-- --------------------------------------------------------->
            @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

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

            @endif
            <!-- -- --------------------------------------------------- --> 

            <!-- -- ================================================== -->
            <!-- -- - password button                           -->
            <!-- -- ================================================== -->
            <div class="row form-group">
                <div class="col-xs-5 padding-top-30">
                    <input style="width : 100%"
                           type="submit"
                           value="Change Password"
                           class="btn btn-primary btn-o pull-right"
                           ng-click="editPassword()"
                           />
                </div>
            </div>

            <!-- -- ================================================== -->
            <!-- -- - save button                           -->
            <!-- -- ================================================== -->
            <div class="row form-group">
                <div class="col-xs-5 padding-top-30">
                    <input style="width : 100%"
                           type="submit"
                           value="Update Profile"
                           class="btn btn-primary btn-o pull-right"
                           ng-click="updateProfile()"
                           />
                </div>
            </div>

        </div>

        <!-- -- ================================================== -->
    </div>
</div>



