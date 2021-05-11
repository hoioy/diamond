export default {
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
        studentClass: '',
        courseName: ''
      }
    },
    initRules() {
      return {
      }
    },
    queryAllStudentCourses(id) {
      this.courses = []
      if (!id) id = this.detail.id
    }

  }
}
