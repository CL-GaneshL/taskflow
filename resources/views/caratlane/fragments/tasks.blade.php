
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Tasks</h1>
        </div>

    </div>
</section>

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="container-fluid container-fullw bg-white no-border">

    <div class="row">

        <div class="col-md-10 col-md-offset-1">

            <!-- ================================================== -->
            <!-- - spinner                                          -->
            <!-- ================================================== -->
            <span 
                us-spinner="{radius:30, width:8, length: 16}" 
                spinner-key="taskSpinner"
                spinner-start-active="false">

            </span> 

            <div ng-controller="taskAllocationCtrl">

                <form class="form-horizontal padding-bottom-10" role="form">

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
                <div class="row col-md-12 padding-top-15 padding-bottom-30">

                    <div class="btn-group col-md-4 padding-left-0">

                        <button
                            class="btn btn-primary" 
                            ng-model="calendarView" 
                            uib-btn-radio="'year'">
                            Year
                        </button>                       

                        <button 
                            class="btn btn-primary" 
                            ng-model="calendarView"
                            uib-btn-radio="'month'">
                            Month
                        </button>

                        <button
                            class="btn btn-primary"
                            ng-model="calendarView"
                            uib-btn-radio="'week'">
                            Week
                        </button>                     

                        <button
                            class="btn btn-primary"
                            ng-model="calendarView" 
                            uib-btn-radio="'day'">
                            Day
                        </button>

                    </div>

                    <!-- ================================================== -->
                    <!-- - navigation buttons                               -->
                    <!-- ================================================== --> 
                    <div class="btn-group col-md-3 padding-left-0">

                        <button
                            class="btn btn-primary"
                            mwl-date-modifier
                            date="calendarDay"
                            decrement="calendarView">
                            <i class="ti-angle-left"></i>

                        </button>

                        <button
                            class="btn btn-default padding-left-5 padding-right-5"
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

                    <!-- ================================================== -->
                    <!-- - test button                                  -->
                    <!-- ================================================== -->
                    <div class="col-md-2">
                        <button class="btn btn-primary btn-o btn-wide pull-right" 
                                ng-click="testTasks()" >
                            Test
                        </button>
                    </div>                    

                    <!-- ================================================== -->
                    <!-- - allocate button                                  -->
                    <!-- ================================================== -->
                    <div class="col-md-2">
                        <button class="btn btn-primary btn-o btn-wide pull-right" 
                                ng-click="allocateTasks()" >
                            Allocate
                        </button>
                    </div>

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

<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->
