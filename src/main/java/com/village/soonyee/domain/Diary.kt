package com.village.soonyee.domain

import javax.persistence.*

@Entity
class Diary(
    val title: String,
    val content: String,
    val date : Int
) {
    @Id
    @GeneratedValue
    @Column(name = "diary_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    var writer: Member? = null

    constructor() : this("","", 0)
}