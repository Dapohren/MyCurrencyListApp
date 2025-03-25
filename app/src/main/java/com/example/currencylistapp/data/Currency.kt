package com.example.currencylistapp.data

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "ValCurs")
data class ValCurs(
    @field:Element(name = "Valute")
    @param:Element(name = "Valute")
    val valutes: List<Valute>
)

@Root(name = "Valute")
data class Valute(
    @field:Element(name = "NumCode")
    @param:Element(name = "NumCode")
    val numCode: String,

    @field:Element(name = "CharCode")
    @param:Element(name = "CharCode")
    val charCode: String,

    @field:Element(name = "Name")
    @param:Element(name = "Name")
    val name: String,

    @field:Element(name = "Value")
    @param:Element(name = "Value")
    val value: String,

    @field:Element(name = "Nominal")
    @param:Element(name = "Nominal")
    val nominal: String,

    @field:Element(name = "VunitRate")
    @param:Element(name = "VunitRate")
    val vunitRate: String,

    @field:Attribute(name = "ID")
    @param:Attribute(name = "ID")
    val id: String
)