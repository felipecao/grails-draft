package draft

class Draft {

    String name
    String address

    static constraints = {
        name(blank: false)
        address(nullable: true)
    }
}
