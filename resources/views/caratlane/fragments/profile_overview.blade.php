
 
<!-- initialize the profile controller -->
<div ng-controller="profileOverviewCtrl">

    <div class="row">
        <div class="col-md-5">
            <div class="user-left">
                <div class="center col-md-12">

                    <!-- ================================================== -->
                    <!-- - employee's profile                               -->
                    <!-- ================================================== -->  
                    <h4><% profile . fullName %></h4>

                    <!-- ================================================== -->
                    <!-- - employee's photo                                 -->
                    <!-- ================================================== -->                 
                    <div flow-init="{singleFile:true}" flow-name="obj.flow" 
                         flow-file-added="!!{png:1,gif:1,jpg:1,jpeg:1}[$file.getExtension()]">

                        <div class="user-image">
                            <div class="thumbnail">

                                <img src="{{ asset('../../img/caratlane/avatar-150.png')}}"
                                     alt="" ng-if="!obj.flow.files.length && noImage">
                                <img ng-src="<% profile . avatar %>"
                                     alt="" ng-if="!obj.flow.files.length && !noImage">
                                <img flow-img="obj.flow.files[0]" 
                                     ng-if="obj.flow.files.length">
                            </div>

                        </div>

                    </div>

                    <hr>

                </div>

                <!-- ================================================== -->
                <!-- - employee's profile                               -->
                <!-- ================================================== -->  
                <table class="table table-condensed col-md-12">
                    <thead></thead>
                    <tbody>
                        <tr>
                            <td class="text-right col-md-3 padding-right-10">Id :</td>
                            <td class="col-md-9"><% profile . employeeId %></td>
                        </tr>
                        <tr>
                            <td class="text-right col-md-3 padding-right-10">location :</td>
                            <td class="col-md-9"><% profile . location %></td>
                        </tr>
                        <tr>
                            <td class="text-right col-md-3 padding-right-10">email :</td>
                            <td class="col-md-9"><% profile . email %></a></td>
                        </tr>
                        <tr>
                            <td class="text-right col-md-3 padding-right-10">phone :</td>
                            <td class="col-md-9"><% profile . phone %></td>
                        </tr>
                        <tr>
                            <td class="text-right col-md-3 padding-right-10">employment :</td>
                            <td class="col-md-9"><% profile . employementType %></td>
                        </tr>
                        <tr>
                            <td class="text-right col-md-3 padding-right-10">productivity :</td>
                            <td class="col-md-9"><% profile . productivity %></td>
                        </tr>

                    </tbody>
                </table>


                <!--<div class="radio clip-radio radio-primary col-md-3 padding-right-10">-->
                <div class='row'>
                    <div class="col-md-12 padding-right-10 text-right ng-hide"
                         ng-show="isTeamLeader === true"
                         >
                        <input type="radio"
                               ng-model="isTeamLeader"
                               id="isTeamLeader" 
                               name="isTeamLeader" 
                               value="isTeamLeader" 
                               />
                        <label for="isTeamLeader">
                            Team Leader
                        </label>
                    </div>
                </div>

                <div class='row'>
                    <div class="col-md-12 padding-right-10 text-right ng-hide"
                         ng-show="isProjectManager === true"
                         >
                        <input type="radio"
                               ng-model="isProjectManager"
                               id="isProjectManager" 
                               name="isProjectManager" 
                               value="isProjectManager" 
                               />
                        <label for="isProjectManager">
                            Project Manager
                        </label>
                    </div>
                </div>

            </div>
        </div>

        <div class="col-md-7">

            <!-- ================================================== -->
            <!-- - recent tasks                                     -->
            <!-- ================================================== -->             
            <div class="panel panel-white" id="activities">
                <div class="panel-heading border-light">
                    <h4 class="panel-title text-primary">Recent Assigned Tasks</h4>
                    <paneltool class="panel-tools" tool-collapse="tool-collapse" tool-refresh="load1" tool-dismiss="tool-dismiss"></paneltool>
                </div>
                <div uib-collapse="activities" ng-init="activities = false" class="panel-wrapper">

                    <div class="panel-body col-md-12">

                        <div class="row col-md-12 text-muted text-small"
                             ng-show="taskAllocations.length === 0"
                             >
                            <p>No allocated tasks !</p>
                        </div>

                        <!-- ================================================== -->
                        <!-- - task description                                 -->
                        <!-- ================================================== -->  
                        <ul class="timeline-xs col-md-12" ng-repeat="task in taskAllocations">

                            <li class="timeline-item">

                                <div class="margin-left-15">

                                    <div class="row col-md-12 text-primary">                           
                                        <% task . timeline0 %>
                                    </div>

                                    <div class="row col-md-12">                                       
                                        <% task . timeline1 %>
                                    </div>

                                    <div class="row col-md-12 padding-left-0">

                                        <p class="col-md-10">                                            
                                            Completed : Duration : 
                                            <span ng-class="{'label label-success' : isDurationSuccess(<% task . completed %>,<% task . duration %>,<% task . completion %>), 'label label-warning' : isDurationWarning(<% task . completed %>,<% task . duration %>,<% task . completion %>) }">
                                                <% task . duration_hm %>    
                                            </span>
                                            , Products :
                                            <span ng-class="{'label label-warning' : isProductSuccess(<% task . nb_products_planned %>,<% task . nb_products_completed %>), 'label label-warning' : isProductWarning(<% task . nb_products_planned %>,<% task . nb_products_completed %>) }">
                                                <% task . nb_products_completed %>
                                            </span>
                                            .                                                   
                                        </p>

                                        <!-- ================================================== -->
                                        <!-- - task update icon                                 -->
                                        <!-- ================================================== -->  
                                        <a href="#"
                                           class="btn btn-transparent btn-xs pull-right" 
                                           ng-click="updateTaskAllocation(<% task . id %> )" >
                                            <i class="fa fa-pencil"></i>
                                        </a>

                                    </div>

                                </div>
                            </li>

                        </ul>
                        <!-- ================================================== -->  

                    </div>

                </div>
            </div>
            <!-- ================================================== -->       
        </div>
    </div>
</div>