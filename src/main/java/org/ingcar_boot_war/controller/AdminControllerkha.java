//package org.ingcar_boot_war.controller;
//
///*관리자 화면을 위한 Controller*/
//
//import jakarta.servlet.http.HttpSession;
//import org.ingcar_boot_war.dao.CommentDAO;
//import org.ingcar_boot_war.dao.ReviewLikeDAO;
//import org.ingcar_boot_war.dto.InformDTO;
//import org.ingcar_boot_war.dto.InquiryDTO;
//import org.ingcar_boot_war.dto.ReviewDTO;
//import org.ingcar_boot_war.dto.UserDTO;
//import org.ingcar_boot_war.service.AdminBoardService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminControllerkha {
//
//    @Autowired
//    private AdminBoardService adminBoardService;
//
//
//
//    // 관리자 메인 페이지 이동
////    @GetMapping
////    public String getAdminMainPage() {
////        return "admin_main"; // admin_main.html로 연결
////    }
//
//
//    //////////////////////////////// 문의 ////////////////////////////////
//
//    // 문의
//    @GetMapping("/inquiry")
//    public String getInquiryPage(@RequestParam(defaultValue = "1") int page, Model model) {
//
//        int totalPages= adminBoardService.board_totalInquiryPages();
//        int offset= adminBoardService.offset(page);
//
//        List<InquiryDTO> inquiries = adminBoardService.getInquiry(offset);
//
//        model.addAttribute("inquiries", inquiries);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", totalPages);
//
//        return "admin_inquiry"; // admin_inquiry.html로 연결
//    }
//
//
//    // 특정 문의사항 detail 화면 이동
//    @GetMapping("/inquiry/comment")
//    public String getInquiryDetail(@RequestParam("inquiry_id") int inquiryId, Model model) {
//
//        InquiryDTO inquiries = adminBoardService.findByIdInquiry(inquiryId);
//        System.out.println("inquiries = " + inquiries);
//
//
//        if (inquiries != null) {
//            model.addAttribute("inquiries", inquiries);
//
//            return "admin_inquiry_detail"; // admin_inquiry_detail.html로 연결
//        } else {
//            return "redirect:/admin/inquiry"; // 문의 목록 페이지로 리디렉션
//        }
//    }
//
//    // 문의 답변 달기
//    @PostMapping("/inquiry/comment")
//    public ResponseEntity<String> addInquiryComment(@RequestParam("inquiry_id") int inquiry_id,
//                                                    @RequestParam("response_title") String response_title,
//                                                    @RequestParam("response_content") String response_content,
//                                                    HttpSession session) {
//
//        UserDTO userSession = (UserDTO) session.getAttribute("user_session");
//        String response_id = userSession.getUser_id(); // 현재 로그인한 관리자 ID 가져오기
//
//        adminBoardService.updateInquiryComment(inquiry_id, response_id, response_title, response_content);
//
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .header(HttpHeaders.LOCATION, "/admin/inquiry/comment?inquiry_id=" + inquiry_id)
//                .build();
//    }
//
//    // 문의 글 삭제
//    @PostMapping("/inquiry/delete")
//    public ResponseEntity<String> deleteInquiry(@RequestBody Map<String, Integer> request) {
//        int inquiry_id = request.get("inquiry_id");
//        adminBoardService.deleteInquiry(inquiry_id);
//        return ResponseEntity.ok().build();
//    }
//
//    // 문의 글 검색
//    @GetMapping("/inquiry/search")
//    public String searchInquiry(@RequestParam("keyword") String keyword,
//                                @RequestParam("categorie") String categorie,
//                                @RequestParam(defaultValue = "1") int page,
//                                Model model) {
//
//        int totalPages= adminBoardService.board_totalInquiryPages();
//        int offset= adminBoardService.offset(page);
//
//        List<InquiryDTO> inquiries;
//
//
//        if (categorie.equals("search_title")) {
//            inquiries = adminBoardService.searchInquiryByTitle(keyword,offset);
//        } else {
//            inquiries = adminBoardService.searchInquiryByContent(keyword,offset);
//        }
//
//        model.addAttribute("inquiries", inquiries);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages",totalPages);
//        return "admin_inquiry";  // Make sure this matches your actual view name
//    }
//
//
//
//
//    //////////////////////////////// 공지 ////////////////////////////////
//
//    // 공지 화면 이동
//    @GetMapping("/inform")
//    public String getInformPage(@RequestParam(defaultValue = "1") int page, Model model) {
//
//
//        int totalPages= adminBoardService.board_totalInformPages();
//        int offset= adminBoardService.offset(page);
//
//
//        List<InformDTO> informs = adminBoardService.getInform(offset);
//
//        model.addAttribute("informs", informs);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages",totalPages);
//
//        return "admin_inform"; // admin_inform.html로 연결
//    }
//
//    // 공지 쓰기 이동
//    @GetMapping("/inform/new")
//    public String newInformPage() {
//        return "admin_inform_new"; // admin_inform_new.html로 연결
//    }
//
//    // 새로운 공지 등록
//    @PostMapping("/inform/new")
//    public ResponseEntity<Void> createInform(@RequestParam("inform_title") String inform_title,
//                                             @RequestParam("inform_content") String inform_content,
//                                             HttpSession session) {
//
//        UserDTO userSession = (UserDTO) session.getAttribute("user_session");
//        String response_id = userSession.getUser_id(); // 현재 로그인한 관리자 ID 가져오기
//
//        adminBoardService.createInform(response_id, inform_title, inform_content);
//
//
//        // InformDTO newInform = new InformDTO();
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .header("Location", "/admin/inform")
//                .build();
//    }
//
//    // 공지 수정 이동
//    @GetMapping("/inform/update")
//    public String updateInformPage() {
//        return "admin_inform_new"; // admin_inform_new.html로 연결
//    }
//
//    // 공지 수정
//    @PostMapping("/inform/update")
//    public ResponseEntity<Void> updateInform(@RequestParam("post_id") Long post_id,
//                                             @RequestParam("inform_title") String inform_title,
//                                             @RequestParam("inform_content") String inform_content,
//                                             HttpSession session) {
//
//        UserDTO userSession = (UserDTO) session.getAttribute("user_session");
//        String response_id = userSession.getUser_id(); // 현재 로그인한 관리자 ID 가져오기
//
//        adminBoardService.updateInform(response_id, inform_title, inform_content);
//
//        adminBoardService.updateInform(String.valueOf(post_id), inform_title, inform_content);
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .header("Location", "/admin/inform")
//                .build();
//    }
//
//    // 공지 삭제
//    @PostMapping("/inform/delete")
//    public ResponseEntity<Void> deleteInform(@RequestBody Map<String, Long> request) {
//        Long post_id = request.get("post_id");
//        adminBoardService.deleteInform(post_id);
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .header("Location", "/admin/inform")
//                .build();
//    }
//
//    // 공지 검색
//    @GetMapping("/inform/search")
//    public String searchInforms(@RequestParam("keyword") String keyword,
//                                @RequestParam("categorie") String categorie,
//                                @RequestParam(defaultValue = "1") int page,
//                                Model model) {
//
//        int totalPages= adminBoardService.board_totalInformPages();
//        int offset= adminBoardService.offset(page);
//
//        List<InformDTO> informs;
//
//        if (categorie.equals("search_title")) {
//            informs = adminBoardService.searchByPostTitle(keyword,offset);
//        } else {
//            informs = adminBoardService.searchInformsByContent(keyword,offset);
//        }
//
//        model.addAttribute("informs", informs);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages",totalPages);
//        System.out.println("model = " + model);
//        return "admin_inform";  // Make sure this matches your actual view name
//    }
//
//
////////////////////////////////// 후기 ////////////////////////////////
//
//    // 후기 화면 이동
//    @GetMapping("/review")
//    public String getReviewPage(@RequestParam(defaultValue = "1") int page, Model model) {
//
//        int totalPages= adminBoardService.board_totalReviewPages();
//        int offset= adminBoardService.offset(page);
//
//        List<ReviewDTO> reviews = adminBoardService.getReview(offset);
//
//
//        model.addAttribute("reviews", reviews);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", totalPages);
//        return "admin_review"; // admin_review.html로 연결
//    }
//
//
//    // 후기 글 삭제
//    @PostMapping("/review/delete")
//    public ResponseEntity<String> deleteReview(@RequestBody Map<String, Integer> request) {
//        int log_id = request.get("log_id");
//        adminBoardService.deleteReview(log_id);
//        //return ResponseEntity.ok().build();
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .header("Location", "/admin/review")
//                .build();
//    }
//
//    // 후기 검색
//    @GetMapping("/review/search")
//    public String searchReviews(@RequestParam("keyword") String keyword,
//                                @RequestParam("categorie") String categorie,
//                                @RequestParam(defaultValue = "1") int page,
//                                Model model) {
//
//        int totalPages= adminBoardService.board_totalReviewPages();
//        int offset= adminBoardService.offset(page);
//
//        List<ReviewDTO> reviews;
//
//        if (categorie.equals("search_title")) {
//            reviews = adminBoardService.searchReviewByTitle(keyword,offset);
//        } else {
//            reviews = adminBoardService.searchReviewByContent(keyword,offset);
//        }
//
//        model.addAttribute("reviews", reviews);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages",totalPages);
//        return "admin_review";  // Make sure this matches your actual view name
//    }
//
//
//
//
//}
