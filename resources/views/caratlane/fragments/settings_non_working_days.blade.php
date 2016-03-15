
<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div class="row">

        <div class="col-md-10 col-md-offset-1">

            <div ng-controller="settingsNonWorkingDaysCtrl" >

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
                    <!-- - Add new non working day                          -->
                    <!-- ================================================== --> 
                    <div class="col-md-3">
                        <button class="btn btn-primary btn-o btn-wide pull-right" 
                                ng-click="addNonWorkingDay()" >
                            Create Non-Working Day
                        </button>
                    </div>

                </div>

                <div class="row col-md-12 padding-15">
                    <!-- ================================================== -->
                    <!-- - the calendar's                                   -->
                    <!-- ================================================== --> 
                    <mwl-calendar
                        events="events"
                        view="calendarView"
                        view-title="calendarTitle"
                        current-day="calendarDay"
                        on-event-times-changed="eventTimesChanged(calendarEvent); calendarEvent.startsAt = calendarNewEventStart; calendarEvent.endsAt = calendarNewEventEnd"
                        edit-event-html="'<div class=\'btn btn-primary btn-sm pull-right\'><i class=\'ti-pencil\'></i></div>'"
                        delete-event-html="'<div class=\'btn btn-danger btn-sm margin-right-10 pull-right\'><i class=\'ti-close\'></i></div>'"
                        on-edit-event-click="editNonWorkingDay(calendarEvent)"
                        on-delete-event-click="deleteNonWorkingDay(calendarEvent)"
                        on-event-click="eventClicked(calendarEvent)"
                        on-view-change-click="eventViewChanged(calendarEvent)"
                        cell-is-open="isCellOpen"
                        day-view-start="06:00"
                        day-view-end="22:00"
                        day-view-split="60"
                        cell-modifier="modifyCell(calendarCell)">
                    </mwl-calendar>

                </div>

            </div>

        </div>

    </div>

</div>

