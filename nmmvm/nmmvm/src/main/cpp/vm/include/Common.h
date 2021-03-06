/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Common defines for all Dalvik code.
 */
#ifndef DALVIK_COMMON_H_
#define DALVIK_COMMON_H_

#ifndef LOG_TAG
# define LOG_TAG "dalvikvm"
#endif

#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <assert.h>
#include <jni.h>
#include <string.h>
#include "cutils/log.h"

#if defined(HAVE_ENDIAN_H)
# include <endian.h>
#else /*not HAVE_ENDIAN_H*/
# define __BIG_ENDIAN 4321
# define __LITTLE_ENDIAN 1234
# if defined(HAVE_LITTLE_ENDIAN)
#  define __BYTE_ORDER __LITTLE_ENDIAN
# else
#  define __BYTE_ORDER __BIG_ENDIAN
# endif
#endif /*not HAVE_ENDIAN_H*/

#if !defined(NDEBUG) && defined(WITH_DALVIK_ASSERT)
# undef assert
# define assert(x) \
    ((x) ? ((void)0) : (ALOGE("ASSERT FAILED (%s:%d): %s", \
        __FILE__, __LINE__, #x), *(int*)39=39, (void)0) )
#endif

#define MIN(x, y) (((x) < (y)) ? (x) : (y))
#define MAX(x, y) (((x) > (y)) ? (x) : (y))

#define LIKELY(exp) (__builtin_expect((exp) != 0, true))
#define UNLIKELY(exp) (__builtin_expect((exp) != 0, false))

#define ALIGN_UP(x, n) (((size_t)(x) + (n) - 1) & ~((n) - 1))
#define ALIGN_DOWN(x, n) ((size_t)(x) & -(n))
#define ALIGN_UP_TO_PAGE_SIZE(p) ALIGN_UP(p, SYSTEM_PAGE_SIZE)
#define ALIGN_DOWN_TO_PAGE_SIZE(p) ALIGN_DOWN(p, SYSTEM_PAGE_SIZE)

#define CLZ(x) __builtin_clz(x)

/*
 * If "very verbose" logging is enabled, make it equivalent to ALOGV.
 * Otherwise, make it disappear.
 *
 * Define this above the #include "Dalvik.h" to enable for only a
 * single file.
 */
/* #define VERY_VERBOSE_LOG */
#if defined(VERY_VERBOSE_LOG)
# define LOGVV      ALOGV
# define IF_LOGVV() IF_ALOGV()
#else
# define LOGVV(...) ((void)0)
# define IF_LOGVV() if (false)
#endif


/*
 * These match the definitions in the VM specification.
 */
typedef uint8_t u1;
typedef uint16_t u2;
typedef uint32_t u4;
typedef uint64_t u8;
typedef int8_t s1;
typedef int16_t s2;
typedef int32_t s4;
typedef int64_t s8;


//#ifdef _LP64
typedef uint64_t regptr_t;
//#else
//typedef uint32_t regptr_t;
//#endif

static inline jlong getLongFromArray(const regptr_t *ptr, int idx) {
    jlong val;
    memcpy(&val, &ptr[idx], 8);
    return val;
}

static inline void putLongToArray(regptr_t *ptr, int idx, jlong val) {
    memcpy(&ptr[idx], &val, 8);
}

static inline jdouble getDoubleFromArray(const regptr_t *ptr, int idx) {
    jdouble dval;
    memcpy(&dval, &ptr[idx], 8);
    return dval;
}

static inline void putDoubleToArray(regptr_t *ptr, int idx, jdouble dval) {
    memcpy(&ptr[idx], &dval, 8);
}



#define OFFSETOF_MEMBER(t, f)         \
  (reinterpret_cast<char*>(           \
     &reinterpret_cast<t*>(16)->f) -  \
   reinterpret_cast<char*>(16))

#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
/* general constants */
enum {
    kNoIndex = 0xffffffff,           /* not a valid index value */
};
/*
 * Enumeration of all the data types.
 */
enum DataType {
    NOT = 0,
    VOID = 1,
    BOOLEAN = 2,
    BYTE = 3,
    SHORT = 4,
    CHAR = 5,
    INT = 6,
    LONG = 7,
    FLOAT = 8,
    DOUBLE = 9,
    OBJECT = 10,
};

#endif  // DALVIK_COMMON_H_
