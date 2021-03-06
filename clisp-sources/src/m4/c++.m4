dnl Copyright (C) 1993-2002 Free Software Foundation, Inc.
dnl This file is free software, distributed under the terms of the GNU
dnl General Public License.  As a special exception to the GNU General
dnl Public License, this file may be distributed as part of a program
dnl that contains a configuration script generated by Autoconf, under
dnl the same distribution terms as the rest of that program.

dnl From Bruno Haible, Marcus Daniels.

AC_PREREQ(2.13)

AC_DEFUN([CL_CC_CPLUSPLUS],
[AC_REQUIRE([AC_PROG_CPP])
AC_CACHE_CHECK(whether using C++, cl_cv_prog_cc_cplusplus, [
AC_EGREP_CPP(yes,[#ifdef __cplusplus
  yes
#endif
], cl_cv_prog_cc_cplusplus=yes, cl_cv_prog_cc_cplusplus=no)
])
if test $cl_cv_prog_cc_cplusplus = yes; then
  CC_CPLUSPLUS=true
else
  CC_CPLUSPLUS=false
fi
AC_SUBST(CC_CPLUSPLUS)dnl
])

AC_DEFUN([CL_CXX_WORKS],
[AC_CACHE_CHECK(whether CXX works at all, cl_cv_prog_cxx_works, [
AC_LANG_SAVE()
AC_LANG_CPLUSPLUS()
AC_TRY_RUN([int main() { exit(0); }],
cl_cv_prog_cxx_works=yes, cl_cv_prog_cxx_works=no,
AC_TRY_LINK([], [], cl_cv_prog_cxx_works=yes, cl_cv_prog_cxx_works=no))
AC_LANG_RESTORE()
])
case "$cl_cv_prog_cxx_works" in
  *no) echo "Installation or configuration problem: C++ compiler cannot create executables."; exit 1;;
  *yes) ;;
esac
])

AC_DEFUN([CL_TEMPLATE_NULL],
[CL_COMPILE_CHECK([working template<>], cl_cv_c_templatenull,
[template <class T> class c {}; template <> class c<int> { int x; };], ,
AC_DEFINE(HAVE_TEMPLATE_NULL))
])
