
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Delete Employee</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <h4>Are you sure you want to delete this Employee ? </h4>

        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="deleteForm" >

            <div class="form-group">
                <label class="control-label">
                    Employee Id :
                </label>
                <input type="text" 
                       class="form-control"
                       name="employeeId"
                       ng-model="employee.employeeId"
                       ng-value ="employee.employeeId"
                       />
            </div>

            <div class="form-group">
                <label class="control-label">
                    Full Name :
                </label>
                <input type="text" 
                       class="form-control"
                       name="fullName"
                       ng-model="employee.fullName"
                       ng-value ="employee.fullName"
                       />
            </div>


        </form> 

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-danger btn-o" ng-click="deleteEmployee()">
            Delete Event
        </button>

    </div>

</div>