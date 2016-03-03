
<!-- ================================================================ -->
<!-- authenticated user role and privileges                           -->
<!-- ================================================================ -->

@inject('authUser', 'App\Helpers\AuthenticatedUser')


<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div class="row">

        <div class="col-md-10 col-md-offset-1">

            <div ng-controller="projectsTasksCtrl">

                <form class="form-horizontal padding-bottom-20" role="form">

                    <fieldset>

                        <legend>
                            Filters :
                        </legend>

                        <div class="row form-group padding-left-10">

                            <!-- ================================================== -->
                            <!-- - projects dropdown menu                           -->
                            <!-- ================================================== --> 
                            <div class="col-md-6"> 
                                <label class="control-label">
                                    Project :
                                </label>
                                <select class="form-control cs-select cs-skin-elastic" 
                                        ng-init="filter_project = projects[0]" 
                                        ng-model="filter_project"
                                        ng-change="changeProject()"
                                        ng-options="project.reference for project in projects" 
                                        >
                                    <option value="" disabled="">Select a project ...</option>
                                </select>

                            </div> 


                            <!-- ================================================== -->
                            <!-- - employees dropdown menu                           -->
                            <!-- ================================================== -->  
                            <div class="col-md-6"> 
                                <label class="control-label">
                                    Employee :
                                </label>
                                <select class="form-control cs-select cs-skin-elastic" 
                                        ng-init="filter_employee = employees[0]" 
                                        ng-model="filter_employee"
                                        ng-change="changeEmployee()"
                                        ng-options="employee.fullName for employee in employees" 
                                        >
                                    <option value="" disabled="">Select an employee ...</option>
                                </select>

                            </div> 

                        </div> 

                    </fieldset>

                </form> 


                <div class="row col-md-12 padding-top-15">

                    <!-- ================================================== -->
                    <!-- - title : period of time coved by the calendar     -->
                    <!-- ================================================== --> 
                    <h2 class="col-md-8 text-left padding-left-0"><% calendarTitle %></h2>

                </div>

                <!-- ================================================== -->
                <!-- - calendar coverage                                -->
                <!-- ================================================== --> 
                <div class="row col-md-12 padding-top-15">

                    <div class="btn-group col-md-5 padding-left-0 padding-bottom-30">

                        <label 
                            class="btn btn-primary text-extra-small" 
                            ng-model="calendarView" 
                            uib-btn-radio="'year'">
                            Year
                        </label>                        

                        <label 
                            class="btn btn-primary text-extra-small" 
                            ng-model="calendarView"
                            uib-btn-radio="'month'">
                            Month
                        </label>

                        <label
                            class="btn btn-primary text-extra-small"
                            ng-model="calendarView"
                            uib-btn-radio="'week'">
                            Week
                        </label>                        

                        <label
                            class="btn btn-primary text-extra-small"
                            ng-model="calendarView" 
                            uib-btn-radio="'day'">
                            Day
                        </label>

                    </div>

                    <!-- ================================================== -->
                    <!-- - navigation buttons                               -->
                    <!-- ================================================== --> 
                    <div class="btn-group col-md-4 padding-left-0">

                        <div class="">

                            <button
                                class="btn btn-primary"
                                mwl-date-modifier
                                date="calendarDay"
                                decrement="calendarView">
                                <i class="ti-angle-left"></i>

                            </button>

                            <button
                                class="btn btn-default"
                                mwl-date-modifier
                                date="calendarDay"
                                set-to-today>
                                Today
                            </button>

                            <button
                                class="btn btn-primary"
                                mwl-date-modifier
                                date="calendarDay"
                                increment="calendarView">
                                <i class="ti-angle-right"></i>
                            </button>

                        </div>

                    </div>

                    <!-- ================================================== -->
                    <!-- - Add new events                                   -->
                    <!-- ================================================== --> 

                    <!-- -- --------------------------------------------------- --> 
                    <!-- -- - editable only by Project Manager                  -->
                    <!-- -- --------------------------------------------------- --> 
                    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

                    <button class="btn btn-primary btn-o btn-wide padding-left-5 pull-right " 
                            ng-click="createTask()" >
                        <s>Create Holiday</s>
                    </button>

                    <button class="btn btn-primary btn-o btn-wide pull-right" 
                            ng-click="createHoliday()" >
                        <s>Create Task</s>
                    </button>

                    @endif
                    <!-- -- --------------------------------------------------- --> 

                </div>

                <div class="row col-md-12 padding-15">
                    <!-- ================================================== -->
                    <!-- - the calendar                                     -->
                    <!-- ================================================== --> 
                    <mwl-calendar
                        events="events"
                        view="calendarView"
                        view-title="calendarTitle"
                        current-day="calendarDay"
                        on-event-times-changed="eventTimesChanged(calendarEvent); calendarEvent.startsAt = calendarNewEventStart; calendarEvent.endsAt = calendarNewEventEnd"
                        edit-event-html="'<i class=\'glyphicon glyphicon-pencil\'></i>'"
                        delete-event-html="'<i class=\'glyphicon glyphicon-remove\'></i>'"
                        on-edit-event-click="editEvent(calendarEvent)"
                        on-delete-event-click="deleteEvent(calendarEvent)"
                        on-event-click="editEvent(calendarEvent)"
                        on-view-change-click="eventViewChanged(calendarEvent)"
                        cell-is-open="isCellOpen"
                        day-view-start="00:00"
                        day-view-end="08:00"
                        day-view-split="15"
                        cell-modifier="modifyCell(calendarCell)">
                    </mwl-calendar>

                </div>

            </div>

        </div>

    </div>

</div>


