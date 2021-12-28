package com.village.soonyee.util.entity

class EntityProvider {

    companion object {
        //속도는 떨어지지만 코드 생산 속도는 편리함으로 개발 속도는 많이 오름.
        //프로토 타입 제작 혹은 속도보다는 개발 속도가 중요한 소규모 프로젝트에서 자주 사용함.
        fun <R : IEntityStruct<T>, T : IEntitySource> loadEntity(entityClass : Class<R>, source : T) : R {
            val instance = entityClass.constructors[0].newInstance()
            source::class.java.declaredFields.forEach {
                entityClass.getField(it.name).set(
                        instance, it.get(source)
                )
            }
            @Suppress("UNCHECKED_CAST")
            return instance as R
        }
    }



}