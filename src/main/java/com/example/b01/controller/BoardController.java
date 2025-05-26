package com.example.b01.controller;

import com.example.b01.dto.BoardDTO;
import com.example.b01.dto.PageRequestDTO;
import com.example.b01.dto.PageResponseDTO;
import com.example.b01.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(Model model, @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);


    }
    @GetMapping("/register")
    public void register() {

    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("boardDTO") BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("Board Post register");
        if (bindingResult.hasErrors()) {
            log.error("Binding Error");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        log.info(boardDTO);
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";

    }
    @GetMapping({"/read","/modify"})
    public void read(@RequestParam Long bno,@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO ,Model model) {
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);

    }
    @PostMapping("/modify")
    public String modify(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, @Valid @ModelAttribute("boardDTO") BoardDTO boardDTO, RedirectAttributes redirectAttributes,BindingResult bindingResult) {
        log.info("Board Post modify");

        if (bindingResult.hasErrors()) {
            log.error("Binding Error");
            String link= pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno",boardDTO.getBno());
            return "redirect:/board/modify?"+link;

        }
        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result","modifyed");
        redirectAttributes.addAttribute("bno",boardDTO.getBno());
        return "redirect:/board/read";
    }
    @PostMapping("/remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes) {
        log.info("Board Post remove");
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result","removed");
        return "redirect:/board/list";

    }
}
