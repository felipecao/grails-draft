import draft.Draft

class BootStrap {

    def init = { servletContext ->

        if(Draft.count() == 0){
            new Draft(name: "Felipe", address: "Brazil").save()
            new Draft(name: "McGregor", address: "Scotland").save()
        }
    }
    def destroy = {
    }
}
