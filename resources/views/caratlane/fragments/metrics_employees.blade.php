

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->

<div class="container-fluid container-fullw bg-white no-border">

    <div class="row col-md-11 col-md-offset-1">

        <div ng-controller="metricsEmployeesCtrl">

            <!-- ================================================== -->
            <!-- - list of employees                                -->
            <!-- ================================================== -->    

            <div class="row">
                <div class="col-md-11 text-center">

                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th class="center col-md-1"></th>
                                <th class="center col-md-1">Id</th>
                                <th class="center col-md-2">Full Name</th>
                                <th class="center col-md-1">Empl.</th>
                                <th class="center col-md-1">Prod.</th>
                                <th class="center col-md-1"></th>
                                <th class="center col-md-1"></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="employee in employees" >
                                <td class ="center col-md-1">
                                    <img class="img-rounded" 
                                         alt="<% employee.fullName %>"
                                         src="{{ asset('../../img/caratlane/avatar-50.png')}}" 
                                         />
                                </td>
                                <td class="hidden-xs col-md-1"> <% employee.employeeId %> </td>
                                <td class="col-md-2"> <% employee.fullName %> </td>
                                <td class="hidden-xs col-md-1"> <% employee.employementType %> </td>
                                <td class="hidden-xs col-md-1"> <% employee.productivity %> </td>
                                <td class="hidden-xs col-md-1"></td>
                                <td class="hidden-xs col-md-1"></td>

                                <!-- ================================================== -->
                                <!-- - edit and remove icons                            -->
                                <!-- ================================================== -->  
                                <td class="center">
                                    <div class="visible-md visible-lg hidden-sm hidden-xs">

                                        // ng-click="displayMetrics( <% employee.id %> )" >
                                        <a href="#"
                                           class="btn btn-transparent btn-xs" 
                                           tooltip-placement="top" 
                                           uib-tooltip="Display Metrics"
                                           ng-click="" >
                                            <i class="fa fa-bar-chart"></i>
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
                                                    // ng-click="displayMetrics( <% employee.id %> )">
                                                    <a href="#"  
                                                       ng-click="">
                                                        Display Metrics
                                                    </a>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>
                                </td>

                            </tr>

                        </tbody>
                    </table>
                </div>
            </div>

            <!-- ================================================== -->

        </div>

    </div>

</div>

<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->


