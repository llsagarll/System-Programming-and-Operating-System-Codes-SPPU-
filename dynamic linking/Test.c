#include "Test.h"

JNIEXPORT jint JNICALL Java_Test_sum(JNIEnv *env, jobject obj, jint n1, jint n2)
{
    return n1+n2;
}
