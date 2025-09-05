import { useEffect, useState, useRef } from "react";
import { Container, Card, Button } from "react-bootstrap";
import { useParams } from "react-router-dom";
import MySpinner from "../../layout/MySpinner";
import WritingEditor from "./WritingEditor";
import SpeakingTask from "./SpeakingTask";
import SectionList from "./SectionList";
import Apis, { endpoints } from "../../configs/Apis";
import rangy from "rangy";
import "rangy/lib/rangy-classapplier";
import parse from "html-react-parser";

const LessonDetail = () => {
    const { lessonId } = useParams();
    const [lesson, setLesson] = useState(null);
    const [loading, setLoading] = useState(true);

    const containerRef = useRef();
    const leftPaneRef = useRef();
    const rightPaneRef = useRef();
    const highlightableRef = useRef();
    const highlightMenuRef = useRef();
    const selectedRangeRef = useRef(null);
    const classApplierRef = useRef(null);

    const [showMenu, setShowMenu] = useState(false);
    const [menuPos, setMenuPos] = useState({ x: 0, y: 0 });

    const durationRef = useRef(0);
    const [displayTime, setDisplayTime] = useState("00:00:00");

    useEffect(() => {
        const timer = setInterval(() => {
            durationRef.current += 1;

            const totalSeconds = durationRef.current;
            const hours = String(Math.floor(totalSeconds / 3600)).padStart(2, '0');
            const minutes = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, '0');
            const seconds = String(totalSeconds % 60).padStart(2, '0');

            setDisplayTime(`${hours}:${minutes}:${seconds}`);
        }, 1000);

        return () => clearInterval(timer);
    }, []);

    useEffect(() => {
        rangy.init();
        classApplierRef.current = rangy.createClassApplier("highlight-yellow", {
            elementTagName: "span",
            normalize: true
        });
    }, []);

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

        handleResponsiveChange();
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

    useEffect(() => {
        const handleMouseUp = (e) => {
            if (
                highlightMenuRef.current &&
                highlightMenuRef.current.contains(e.target)
            ) return;

            const sel = window.getSelection();
            const text = sel.toString().trim();

            if (!text || !highlightableRef.current.contains(sel.anchorNode)) {
                setShowMenu(false);
                return;
            }

            const range = sel.getRangeAt(0);
            selectedRangeRef.current = range.cloneRange();

            const rect = range.getBoundingClientRect();
            setMenuPos({
                x: rect.left + window.scrollX,
                y: rect.top + window.scrollY - 40,
            });
            setShowMenu(true);
        };

        document.addEventListener("mouseup", handleMouseUp);
        return () => document.removeEventListener("mouseup", handleMouseUp);
    }, []);

    const handleHighlight = () => {
        if (selectedRangeRef.current && classApplierRef.current) {
            const sel = rangy.getSelection();
            sel.removeAllRanges();
            sel.addRange(selectedRangeRef.current);
            classApplierRef.current.applyToSelection();
        }
        setShowMenu(false);
    };

    const handleUnhighlight = () => {
        if (selectedRangeRef.current && classApplierRef.current) {
            const sel = rangy.getSelection();
            sel.removeAllRanges();
            sel.addRange(selectedRangeRef.current);
            classApplierRef.current.undoToSelection();
        }
        setShowMenu(false);
    };

    if (loading) return <MySpinner />;
    if (!lesson) return <p className="text-center">Bài học không tồn tại.</p>;

    const renderExerciseComponent = () => {
        const type = lesson.skill.toLowerCase();
        switch (type) {
            case "writing":
                return <WritingEditor lessonId={lesson.id} lessonContent={lesson.content} lessonType={lesson.lessonTypeName} durationRef={durationRef} />;
            case "speaking":
                return <SpeakingTask lessonId={lesson.id} lessonType={lesson.lessonTypeName} durationRef={durationRef} />;
            case "reading":
            case "listening":
                return <SectionList lessonId={lesson.id} durationRef={durationRef} />;
            default:
                return <p className="text-danger">Không hỗ trợ loại bài này!</p>;
        }
    };

    return (
        <Container className="my-4">
            <div className="d-flex justify-content-end align-items-center mb-2">
                <span className="badge bg-primary fs-6">
                    Thời gian làm bài: {displayTime}
                </span>
            </div>
            <div className="split-layout" ref={containerRef}>
                <div ref={highlightableRef} style={{ display: "flex", flexDirection: "row", width: "100%" }}>
                    <div className="left-pane" ref={leftPaneRef}>
                        <Card className="mb-4 shadow">
                            <Card.Img variant="top" src={lesson.image} alt={lesson.title} style={{ maxWidth: "200px", objectFit: "cover", margin: "10px" }} />
                            <Card.Body>
                                <Card.Title>{lesson.title}</Card.Title>
                                <div className="card-text lesson-content">
                                    {parse(lesson.content)}
                                </div>
                            </Card.Body>
                        </Card>
                    </div>

                    <div className="right-pane" ref={rightPaneRef}>
                        {renderExerciseComponent()}
                    </div>
                </div>
            </div>

            {showMenu && (
                <div
                    ref={highlightMenuRef}
                    className="highlight-toolbar position-absolute"
                    style={{ top: `${menuPos.y}px`, left: `${menuPos.x}px` }}
                    onMouseDown={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                    }}
                    onMouseUp={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                    }}
                >
                    <Button variant="info" size="sm" onClick={handleHighlight}>🔆 Highlight</Button>
                    <Button variant="outline-secondary" size="sm" onClick={handleUnhighlight}>❌ Bỏ</Button>
                </div>
            )}
        </Container>
    );
};

export default LessonDetail;
