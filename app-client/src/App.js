import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./components/Home";
import Header from "./layout/Header";
import Footer from "./layout/Footer";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import Login from "./components/user/Login";
import Register from "./components/user/Register";
import VerifyOtp from "./components/user/VerifyOtp";
import Logout from "./components/user/Logout";
import LessonList from "./components/practice/LessonList";
import LessonDetail from "./components/practice/LessonDetail";
import Profile from "./components/user/Profile";
import WritingResult from "./components/practice/WritingResult";
import './App.css';

const App = () => {

  return (
    <>
      <BrowserRouter>
        <div style={{
          display: "flex",
          flexDirection: "column",
          minHeight: "100vh"
        }}>
          <Header />

          {/* Nội dung chính chiếm hết phần còn lại */}
          <main style={{ flex: 1 }}>
            <Container className="py-4">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/verify-otp" element={<VerifyOtp />} />
                <Route path="/logout" element={<Logout />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/lessons" element={<LessonList />} />
                <Route path="/lessons/:lessonId" element={<LessonDetail />} />
                <Route path="/writing-result/:id" element={<WritingResult />} />
                {/* Thêm các route khác nếu cần */}
              </Routes>
            </Container>
          </main>

          <Footer />
        </div>
      </BrowserRouter>
    </>
  );
}
export default App;