import axios from "axios";

const SERVER_CONTEXT = "AppV1";

export const endpoints = {
    "category-types": "/category-types",
    "categories": "/categories",
    "login": "/login",
    "register": "/register",
    "request-otp": `/request-otp`,
    "verify-otp": `/verify-otp`,
    "lessons": "/lessons",
    "lessons-count": "/lessons/count",
    "lesson-detail": (lessonId) => `/lessons/${lessonId}`,
    "speaking-lesson-detail": (lessonId) => `/speaking-lessons/${lessonId}`,
    "writing-evaluate": "/writing/evaluate",
    "writing-result": (answerId) => `/writing/${answerId}`,
    "speaking-evaluate": "/speaking/evaluate",
    "speaking-result": (answerId) => `/speaking/${answerId}`,
    "sections": (lessonId) => `/lessons/${lessonId}/sections`,
    "practice-evaluate": "/practice/evaluate",
    // Add other endpoints as needed
};

export default axios.create({
    baseURL: `http://localhost:8080/${SERVER_CONTEXT}/api`, // Base URL for API requests
});