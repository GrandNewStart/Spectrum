package com.spectrum.spectrum.src.models

/*
* location: 0(등록안됨), 1(수도권), 2(지방), 3(해외), 4(지거국), 5(선택안함)
* graduate: 0(등록안됨), 1(졸업), 2(재학중), 3(휴학), 4(중퇴)
* degree: 0(등록안됨), 1(고졸이하), 2(고등학교), 3(대학교-2,3년제), 4(대학교-4년제), 5(석사), 6(박사), 7(박사이상)
* grade: 4.5 총점으로 환산
* */

data class Education(
    var location: Location?,
    var graduate: Graduate?,
    var degree: Degree?,
    var schoolName: String?,
    var major: String?,
    var grade: Double?
)