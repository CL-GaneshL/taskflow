
<div class="container-fluid container-fullw bg-white no-border" >    

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Java Configuration</h3>
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

                <legend>
                    Configuration :
                </legend>

                <!-- ================================================== -->
                <!-- - title                                            -->
                <!-- ================================================== -->
                <div class="form-group padding-top-5 padding-left-10">

                    <ul class="timeline-xs col-md-12" ng-repeat="log in logs">

                        <li>
                            <!--<div class="row col-md-12 text-muted text-small">-->
                            <i class="fa fa-time"></i> 
                            <p><% log.msg %></p>
                            <!--</div>-->
                        </li>

                    </ul>

                </div>

            </fieldset>

            <!-- ================================================== -->
        </form>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-md-12">

        <div class="row form-group">

            <button class="btn btn-primary btn-o" ng-click="ok()">
                OK
            </button>

        </div>

    </div>

</div>