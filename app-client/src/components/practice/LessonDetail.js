import { useEffect, useState, useRef } from "react";
import { Container, Card } from "react-bootstrap";
import { useParams } from "react-router-dom";
import MySpinner from "../../layout/MySpinner";
import WritingEditor from "./WritingEditor";
import SpeakingTask from "./SpeakingTask";
import SectionList from "./SectionList";
import Apis, { endpoints } from "../../configs/Apis";

const LessonDetail = () => {
    const { lessonId } = useParams();
    const [lesson, setLesson] = useState(null);
    const [loading, setLoading] = useState(true);

    const leftPaneRef = useRef();

    // 🧠 Reset layout khi responsive thay đổi
    useEffect(() => {
        const handleResponsiveChange = () => {
            const isMobile = window.innerWidth <= 768;
            const leftPane = leftPaneRef.current;
            if (!leftPane) return;

            if (isMobile) {
                leftPane.style.width = "100%";
                leftPane.style.height = "200px";
            } else {
                leftPane.style.height = "100%";
                leftPane.style.width = "50%";
            }
        };

        handleResponsiveChange(); // chạy lần đầu
        window.addEventListener("resize", handleResponsiveChange);

        return () => window.removeEventListener("resize", handleResponsiveChange);
    }, []);

    useEffect(() => {
        const fetchLesson = async () => {
            try {
                const res = await Apis.get(`${endpoints["lesson-detail"](lessonId)}`);
                setLesson(res.data);
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchLesson();
    }, [lessonId]);

    if (loading) return <MySpinner />;
    if (!lesson) return <p className="text-center">Bài học không tồn tại.</p>;

    const renderExerciseComponent = () => {
        const type = lesson.skill.toLowerCase();
        switch (type) {
            case "writing":
                return <WritingEditor lessonId={lesson.id} lessonContent={lesson.content} lessonType={lesson.lessonTypeName} />;
            case "speaking":
                return <SpeakingTask lessonId={lesson.id} lessonType={lesson.lessonTypeName} />;
            case "reading":
            case "listening":
                return <SectionList lessonId={lesson.id} />;
            default:
                return <p className="text-danger">Không hỗ trợ loại bài này!</p>;
        }
    };

    return (
        <Container className="my-4">
            <div className="split-layout">
                <div className="left-pane" ref={leftPaneRef}>
                    <Card className="mb-4 shadow">
                        <Card.Img variant="top" src={lesson.image} alt={lesson.title} style={{ maxWidth: "200px", objectFit: "cover", margin: "10px" }} />
                        <Card.Body>
                            <Card.Title>{lesson.title}</Card.Title>
                            <div className="card-text lesson-content" dangerouslySetInnerHTML={{ __html: lesson.content }} />
                        </Card.Body>
                    </Card>
                </div>

                <div className="right-pane">
                    {renderExerciseComponent()}
                </div>
            </div>
        </Container>
    );
};

export default LessonDetail;
