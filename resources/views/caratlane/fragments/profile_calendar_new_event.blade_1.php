
<div class="container-fluid container-fullw bg-white no-border" >    

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-xs-12">
        <h3 class="modal-title">New Event</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-xs-12">

        <!--------------------------------------------------------->
        <!-- type of event radio buttons                         -->
        <!--------------------------------------------------------->
        <div class="form-group">

            <label class="row  col-xs-12">
                Type of Event
            </label>

            <div class="row col-xs-12" ng-init="event.type">

                <!-- task radio button -->
                <div class="radio clip-radio radio-primary col-xs-4 no-margin">
                    <input type="radio"
                           ng-model="event.type"
                           id="job" 
                           name="optionsCategory" 
                           value="job" 
                           />
                    <label for="job">
                        <span class="fa fa-circle text-primary"></span> 
                        Task
                    </label>
                </div>

                <!-- Off radio button -->
                <div class="radio clip-radio radio-primary col-xs-4 no-margin">
                    <input type="radio"
                           ng-model="event.type"
                           id="generic"
                           name="optionsCategory"
                           value="off-site-work"
                           />
                    <label for="off-site-work">
                        <span class="fa fa-circle text-green"></span>
                        Off
                    </label>
                </div>

                <!-- holiday radio button -->
                <div class="radio clip-radio radio-primary col-xs-4 no-margin">
                    <input type="radio"
                           ng-model="event.type"
                           id="to-do" 
                           name="optionsCategory"
                           value="to-do"                              
                           />
                    <label for="to-do">
                        <span class="fa fa-circle text-orange"></span>
                        Holiday
                    </label>
                </div>
            </div>

        </div>

    </div>

    <!--------------------------------------------------------->
    <!-- task                                                -->
    <!--------------------------------------------------------->
    <div class="row form-group col-xs-12"
         ng-show="event.type === 'job'" >
        <label>
            Task
        </label>
        <input type="text" 
               ng-model="event.title" 
               placeholder="event.title" 
               class="form-control underline text-large"                        
               />
    </div>

    <!--------------------------------------------------------->
    <!-- start date                                          -->
    <!--------------------------------------------------------->
    <div class="form-group col-xs-12">

        <div class="form-group col-xs-6 no-padding">
            <label>
                Start
            </label>
            <span class="input-icon">
                <input type="text" 
                       ng-model="event.startsAt" 
                       ng-init="startOpen = false"
                       class="form-control underline"
                       ng-click="startOpen = !startOpen" 
                       uib-datepicker-popup="fullDate"             
                       is-open="startOpen"                              
                       max-date="event.endsAt" 
                       close-text="Close" 
                       />
                <i class="ti-calendar"></i>
            </span>
            <uib-timepicker
                ng-model="event.startsAt"
                show-meridian="true" 
                ng-show="!event.allDay">                    
            </uib-timepicker>
        </div>

        <!--------------------------------------------------------->
        <!-- end date                                            -->
        <!--------------------------------------------------------->
        <div class="form-group col-xs-6 no-padding">
            <label>
                End
            </label>
            <span class="input-icon">
                <input type="text"
                       ng-model="event.endsAt" 
                       ng-init="endOpen = false"
                       class="form-control underline"
                       ng-click="endOpen = !endOpen"
                       uib-datepicker-popup="fullDate"
                       is-open="endOpen"                             
                       min-date="event.startsAt"
                       close-text="Close" 
                       />
                <i class="ti-calendar"></i>
            </span>
            <uib-timepicker 
                ng-model="event.endsAt" 
                show-meridian="true"
                ng-show="!event.allDay">                                
            </uib-timepicker>
        </div>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-xs-12">

        <div class="row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-danger btn-o" ng-click="cancel()">
            Save
        </button>
            
<!--            <button type="button"
                    class="btn btn-default btn-danger btn-o" 
                    ng-click="cancel()" >
                Cancel
            </button>

            <button type="button"
                    class="btn btn-primary btn-o"
                    ng-click="ok()">
                Save
            </button>-->

        </div>

    </div>

</div>