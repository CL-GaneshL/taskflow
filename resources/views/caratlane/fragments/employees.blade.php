
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">
        
        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Employees</h1>
        </div>
    </div>
</section>


<div class="container-fluid container-fullw" >

    <div ng-controller="employeesCtrl"> 

        <!-- ================================================== -->
        <!-- - new employee button                              -->
        <!-- ================================================== -->

        <div class="row">

            <div class="col-md-11">
                <button class="btn btn-primary btn-o btn-wide pull-right" 
                        ng-click="newEmployee()" 
                        >
                    New Employee
                </button>
            </div>

            </br></br></br>

        </div>

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
                            <th class="center col-md-1">Location</th>
                            <th class="center col-md-1">Email</th>
                            <th class="center col-md-1">Phone</th>
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
                            <td class="hidden-xs col-md-1"> <% employee.location %> </td>
                            <td class="hidden-xs col-md-1"> <% employee.email %> </td>
                            <td class="hidden-xs col-md-1"> <% employee.phone %> </td>

                            <!-- ================================================== -->
                            <!-- - edit and remove icons                            -->
                            <!-- ================================================== -->  
                            <td class="center">
                                <div class="visible-md visible-lg hidden-sm hidden-xs">

                                    <a href="#"
                                        class="btn btn-transparent btn-xs" 
                                        tooltip-placement="top" 
                                        uib-tooltip="Edit Employee"
                                        ng-click="showEmployee( <% employee.id %> )" >
                                        <i class="fa fa-pencil"></i>
                                    </a>

                                    <a href="#"
                                        class="btn btn-transparent btn-xs tooltips" 
                                        tooltip-placement="top"
                                        uib-tooltip="Remove Employee"
                                        ng-click="deleteEmployee( <% employee.id %> )" >
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
                                                   ng-click="editEmployee( <% employee.id %> )">
                                                    Edit
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" 
                                                   ng-click="deleteEmployee( <% employee.id %> )" >
                                                    Remove
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

    </div>
</div>
