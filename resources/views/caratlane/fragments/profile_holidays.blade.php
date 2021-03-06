
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

            <div ng-controller="profileHolidaysCtrl" >

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

                    <!-- -- --------------------------------------------------- --> 
                    <!-- -- - editable only by Project Manager                  -->
                    <!-- -- --------------------------------------------------- --> 
                    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

                    <!-- ================================================== -->
                    <!-- - create new task and holiday button               -->
                    <!-- ================================================== --> 
                    <div class="col-md-5 padding-0">
                        
                        <div class="pull-right">
                            <button class="btn btn-primary btn-o" 
                                    ng-click="createHoliday()" >
                                Create Holiday
                            </button>
                        </div>

                    </div>

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
                        delete-event-html="'<i class=\'glyphicon glyphicon-remove\'></i>'"
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

