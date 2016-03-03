
<div class="container-fluid container-fullw bg-white no-border" >    

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Delete Holidays</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new non working day form                         -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" role="form">


            <!-- ================================================== -->
            <!-- - title                                             -->
            <!-- ================================================== -->
            <fieldset>

                <legend>
                    Holiday :
                </legend>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <input type="text" 
                               ng-model="event.title" 
                               value="<% event.title %>"
                               class="form-control underline text-large" 
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>

            <!-- ================================================== -->
            <!-- - start date                                       -->
            <!-- ================================================== -->
            <fieldset>

                <legend>
                    Start Date :
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
                                   ng-readonly="true"
                                   disabled="" />

                            <i class="ti-calendar"></i>
                        </span>

                        <div class="form-group col-md-12 padding-top-15">

                            <div class="row">

                                <!-- general non working day -->
                                <div class="radio clip-radio radio-primary col-md-6 padding-0 text-center" 
                                     ng-init="event.start_morning_shift">

                                    <input type="radio"
                                           ng-model="event.start_morning_shift"
                                           id="start_morning_shift"
                                           value="true" 
                                           ng-checked="event.start_morning_shift === true"
                                           ng-readonly="true"
                                           disabled="" />

                                    <label for="start_morning_shift">
                                        Morning shift
                                    </label>
                                </div>

                                <!-- week end non working day -->
                                <div class="radio clip-radio radio-primary col-md-6 padding-0 text-center" 
                                     ng-init="event.start_afternoon_shift">

                                    <input type="radio"
                                           ng-model="event.start_afternoon_shift"
                                           id="start_afternoon_shift"
                                           value="true"
                                           ng-checked="event.start_afternoon_shift === true"
                                           ng-readonly="true"
                                           disabled="" />

                                    <label for="start_afternoon_shift">
                                        Afternoon shift
                                    </label>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </fieldset>

            <!-- ================================================== -->
            <!-- - end date                                          -->
            <!-- ================================================== -->
            <fieldset>

                <legend>
                    End Date :
                </legend>

                <div class="form-group padding-left-10">

                    <div class="row col-md-12">

                        <span class="input-icon">
                            <input type="text"
                                   ng-model="event.end_date" 
                                   ng-init="endOpen = false"
                                   class="form-control underline"
                                   ng-click="endOpen = !endOpen"
                                   uib-datepicker-popup="fullDate"
                                   is-open="endOpen"                             
                                   min-date="event.startsAt"
                                   max-date="event.max_date"                                     
                                   close-text="Close" 
                                   ng-readonly="true"
                                   disabled="" />
                            <i class="ti-calendar"></i>
                        </span>

                        <div class="form-group col-md-12 padding-top-15">

                            <div class="row">

                                <!-- general non working day -->
                                <div class="radio clip-radio radio-primary col-md-6 padding-0 text-center" 
                                     ng-init="event.end_morning_shift">

                                    <input type="radio"
                                           ng-model="event.end_morning_shift"
                                           id="end_morning_shift"
                                           value="true" 
                                           ng-checked="event.end_morning_shift === true"
                                           ng-readonly="true"
                                           disabled="" />
                                    <label for="end_morning_shift">
                                        Morning shift
                                    </label>
                                </div>

                                <!-- week end non working day -->
                                <div class="radio clip-radio radio-primary col-md-6 padding-0 text-center" 
                                     ng-init="event.end_afternoon_shift">

                                    <input type="radio"
                                           ng-model="event.end_afternoon_shift"
                                           id="end_afternoon_shift"
                                           value="true"
                                           ng-checked="event.end_afternoon_shift === true"
                                           ng-readonly="true"
                                           disabled="" />
                                    <label for="end_afternoon_shift">
                                        Afternoon shift
                                    </label>
                                </div>

                            </div>

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

            <button class="btn btn-primary btn-o" ng-click="delete()">
                Delete
            </button>

        </div>

    </div>

</div>