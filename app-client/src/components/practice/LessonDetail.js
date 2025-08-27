import { useEffect, useState } from "react";
import { Container, Row, Col, Card } from "react-bootstrap";
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

    useEffect(() => {
        if (!lesson) return;

        const imgs = document.querySelectorAll(".lesson-content img");
        imgs.forEach(img => {
            img.removeAttribute("width");
            img.removeAttribute("height");
            img.removeAttribute("style");

            // Thêm lại style như mong muốn
            img.style.maxWidth = "100%";
            img.style.height = "auto";
            img.style.display = "block";
            img.style.margin = "0 auto";
        });
    }, [lesson]);

    if (loading) return <MySpinner />;
    if (!lesson) return <p className="text-center">Bài học không tồn tại.</p>;

    const renderExerciseComponent = () => {
        const type = lesson.skill.toLowerCase();
        switch (type) {
            case "writing":
                return <WritingEditor lessonId={lesson.id} lessonContent={lesson.content} lessonType={ lesson.lessonTypeName } />;
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
            <Row>
                <Col md={6}>
                    <Card className="mb-4 shadow">
                        <Card.Img variant="top" src={lesson.image} alt={lesson.title} style={{ maxWidth: "200px", objectFit: "cover", margin: "10px" }} />
                        <Card.Body>
                            <Card.Title>{lesson.title}</Card.Title>
                            <div className="card-text lesson-content" dangerouslySetInnerHTML={{ __html: lesson.content }} />
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={6}>{renderExerciseComponent()}</Col>
            </Row>
        </Container>
    );
};

export default LessonDetail;