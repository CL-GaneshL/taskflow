
<div class="container-fluid container-fullw bg-white no-border" >    

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-xs-12">
        <h3 class="modal-title">Create Event</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new non working day form                         -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" role="form">

            <fieldset>

                <!-- ================================================== -->
                <!-- - title                                            -->
                <!-- ================================================== -->

                <legend>
                    Event :
                </legend>

                <div class="form-group padding-top-5 padding-left-10">

                    <label class="row col-md-12">
                        Title :
                    </label>

                    <div class="row col-md-12">

                        <input type="text" 
                               ng-model="event.title" 
                               placeholder="Enter a title" 
                               class="form-control underline text-large" 
                               ng-trim="true"
                               required />

                    </div>

                </div>

                <!-- ================================================== -->
                <!-- - event type                                       -->
                <!-- ================================================== -->
                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12" ng-init="event.type">

                        <div class="radio clip-radio radio-warning col-md-5 text-left padding-left-15">
                            <input type="radio"
                                   ng-model="event.type"
                                   id="HOLIDAYS"
                                   value="HOLIDAYS" 
                                   ng-checked="1"
                                   />
                            <label for="HOLIDAYS">
                                Holidays
                            </label>
                        </div>

                        <div class="radio clip-radio radio-primary col-md-5 text-left padding-left-15">
                            <input type="radio"
                                   ng-model="event.type"
                                   id="TASK"
                                   value="TASK"
                                   />
                            <label for="TASK">
                                Task
                            </label>
                        </div>

                    </div>

                </div>

            </fieldset>

            <!-- ================================================== -->
            <!-- - task start date and duration                        -->
            <!-- ================================================== -->         
            <fieldset>

                <legend>
                    Start date :
                </legend>

            </fieldset>

            <!-- ================================================== -->
            <!-- - start date                                       -->
            <!-- ================================================== -->         
            <fieldset>

                <legend>
                    Start date :
                </legend>

                <div class="form-group padding-left-10">

                    <div class="row col-md-12">

                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="event.start_date" 
                                   ng-init="startOpen = false"
                                   class="form-control underline"
                                   ng-click="startOpen = !startOpen" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen"     
                                   min-date="event.min_date"
                                   max-date="event.max_date" 
                                   close-text="Close" 
                                   />
                            <i class="ti-calendar"></i>
                        </span>

                    </div>

                </div>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <div class="radio clip-radio radio-primary col-md-5 text-left padding-left-15" 
                             ng-init="event.morning_shift">

                            <input type="radio"
                                   ng-model="event.morning_shift"
                                   id="morning_shift"
                                   value="true" />

                            <label for="morning_shift">
                                Morning Shift
                            </label>
                        </div>

                        <div class="radio clip-radio radio-primary col-md-5 text-left padding-left-15"
                             ng-init="event.afternoon_shift">

                            <input type="radio"
                                   ng-model="event.afternoon_shift"
                                   id="afternoon_shift"
                                   value="true" />

                            <label for="afternoon_shift">
                                Afternoon Shift
                            </label>
                        </div>

                    </div>

                </div>

            </fieldset>

            <!-- ================================================== -->
            <!-- - end date                                       -->
            <!-- ================================================== -->         
            <fieldset>

                <legend>
                    End date :
                </legend>

                <div class="form-group padding-left-10">

                    <div class="row col-md-12">

                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="event.end_date" 
                                   ng-init="startOpen = false"
                                   class="form-control underline"
                                   ng-click="startOpen = !startOpen" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen"     
                                   min-date="event.min_date"
                                   max-date="event.max_date" 
                                   close-text="Close" 
                                   />
                            <i class="ti-calendar"></i>
                        </span>

                    </div>

                </div>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <div class="radio clip-radio radio-primary col-md-5 text-left padding-left-15" 
                             ng-init="event.morning_shift">

                            <input type="radio"
                                   ng-model="event.morning_shift"
                                   id="morning_shift"
                                   value="true" />

                            <label for="morning_shift">
                                Morning Shift
                            </label>
                        </div>

                        <div class="radio clip-radio radio-primary col-md-5 text-left padding-left-15"
                             ng-init="event.afternoon_shift">

                            <input type="radio"
                                   ng-model="event.afternoon_shift"
                                   id="afternoon_shift"
                                   value="true" />

                            <label for="afternoon_shift">
                                Afternoon Shift
                            </label>
                        </div>

                    </div>

                </div>

            </fieldset>


        </form>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-md-12">

        <div class="row form-group">

            <button class="btn btn-primary btn-o" ng-click="cancel()">
                Cancel
            </button>

            <button class="btn btn-primary btn-o" ng-click="create()">
                Create
            </button>

        </div>

    </div>

</div>