package com.example.wirtualnaprzychodnia

class Uzytkownik {

    var imie: String=""

    var nazwisko: String=""

    var email: String=""

    var telefon: String=""

    var nick: String=""


    constructor(){}

    constructor(imie: String, nazwisko: String, email: String, telefon: String, nick: String)
    {
        this.imie=imie
        this.nazwisko=nazwisko
        this.email=email
        this.telefon=telefon
        this.nick=nick
    }


}